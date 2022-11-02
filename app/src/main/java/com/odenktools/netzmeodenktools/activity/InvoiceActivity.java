package com.odenktools.netzmeodenktools.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.odenktools.netzmeodenktools.databinding.ActivityInvoiceBinding;
import com.odenktools.netzmeodenktools.internet.ApiEndpoint;
import com.odenktools.netzmeodenktools.internet.RestApi;
import com.odenktools.netzmeodenktools.model.MerchantInvoiceBuyer;
import com.odenktools.netzmeodenktools.model.PaymentMethods;
import com.odenktools.netzmeodenktools.model.request.InvoiceWaStoreRequest;
import com.odenktools.netzmeodenktools.model.request.MerchantPayInvoiceRequest;
import com.odenktools.netzmeodenktools.model.request.body.InvoiceWaStoreRequestBody;
import com.odenktools.netzmeodenktools.model.request.body.MerchantPayInvoiceRequestBody;
import com.odenktools.netzmeodenktools.model.request.body.PayAmount;
import com.odenktools.netzmeodenktools.util.Constanta;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class InvoiceActivity extends AppCompatActivity {

    private ActivityInvoiceBinding binding = null;

    private final String CUSTOMER_PHONE_NUMBER = "+6289671000082";
    private final String CUSTOMER_EMAIL = "rahyan@netzme.id";
    private final String CUSTOMER_ADDRESS = "Guntur bumi";
    private final String CUSTOMER_NETZME_ID = "re3WVWvE";
    private final String CUSTOMER_FULL_NAME = "Rahyan Buyer Tester";
    private final String CUSTOMER_BUY_DESC = "Beli baju @x1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fireButtonAction();
    }

    private void fireButtonAction() {
        binding.btnCreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInvoiceWaStore();
            }
        });
    }

    private void createInvoiceWaStore() {
        InvoiceWaStoreRequest request = new InvoiceWaStoreRequest();
        request.setRequestId(Constanta.RND_UUID);
        request.setType(Constanta.WA_STORE_INVOICE);
        InvoiceWaStoreRequestBody body = new InvoiceWaStoreRequestBody();
        body.trxSource = "rahyan-aggregator";
        body.merchantId = "M_YDXafq2J";
        body.descFromMerchant = CUSTOMER_BUY_DESC;
        body.descFromBuyer = CUSTOMER_BUY_DESC;
        body.fullNameBuyer = CUSTOMER_FULL_NAME;
        body.emailBuyer = CUSTOMER_EMAIL;
        body.phoneNoBuyer = CUSTOMER_PHONE_NUMBER;
        body.callbackUrl = "https://google.com";
        body.invoicePurpose = "Beli baju";
        body.feeType = Constanta.FEE_ON_BUYER;
        body.invoiceType = Constanta.WA_STORE_INVOICE_TYPE_SINGLE;
        body.urlPhoto = "https://odenktools.com/wp-content/uploads/2016/09/odenktools-logos.png";
        body.expireInSecond = Constanta.EXPIRED_ONE_HOUR;
        body.commissionPercentage = 0;
        body.paymentMethods = PaymentMethods.addAllPaymentMethod();

        PayAmount payAmount = new PayAmount();
        payAmount.basicAmount = Constanta.MONEY_TO_PAY.getAmount().floatValue();
        payAmount.shippingAmount = Constanta.MONEY_ZERO.getAmount().floatValue();
        body.payAmount = payAmount;

        request.setBody(body);

        HashMap<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", "Basic a2FyZTpkZGUyOGM3ZWRiOWQ4MDQ2OTcyYzVjMzI3N2Q5OTlhMg==");
        RestApi restApi = RestApi.singleton();
        Call<String> api = restApi
                .setRequestHeaders(authHeader)
                .service(ApiEndpoint.class, Constanta.APP_BASE_URL)
                .createInvoiceWaStore(request);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        JSONObject jsonRoot = new JSONObject(response.body());
                        String requestId = jsonRoot.getString("requestId");
                        Integer status = jsonRoot.getInt("status");
                        JSONObject jsonBody = jsonRoot.getJSONObject("body");
                        String userId = jsonBody.getString("userId");
                        String invoiceId = jsonBody.getString("invoiceId");
                        String urlInvoice = jsonBody.getString("urlInvoice");
                        InvoiceActivity.this.payInvoice(invoiceId);
                    } catch (JSONException e) {
                        Timber.e(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    /**
     * {{url-toko-kare}}/api/merchant/invoice/{{invoice_id}}/pay
     */
    private void payInvoice(String invoiceId) {
        MerchantPayInvoiceRequest request = new MerchantPayInvoiceRequest();
        request.setRequestId(Constanta.RND_UUID);
        request.setType("pay_invoice");

        MerchantPayInvoiceRequestBody body = new MerchantPayInvoiceRequestBody();
        body.setFeeType(Constanta.FEE_ON_BUYER);
        body.setInvoiceId(invoiceId);
        body.setPaidAmount(Constanta.MONEY_TO_PAY);
        body.setAmount(Constanta.MONEY_TO_PAY);
        body.setFeeAmount(Constanta.MONEY_ZERO);

        MerchantInvoiceBuyer buyer = new MerchantInvoiceBuyer();
        buyer.setFullName(CUSTOMER_FULL_NAME);
        buyer.setPhoneNo(CUSTOMER_PHONE_NUMBER);
        buyer.setEmail(CUSTOMER_EMAIL);
        buyer.setAddress(CUSTOMER_ADDRESS);
        buyer.setNetzmeId(CUSTOMER_NETZME_ID);
        buyer.setMaskingName(false);
        buyer.setCity("Bandung");
        buyer.setPostcode("40264");
        buyer.setCountry("Indonesia");
        buyer.setDesc(CUSTOMER_BUY_DESC);
        body.setBuyer(buyer);
        request.setBody(body);

        HashMap<String, String> authHeader = new HashMap<>();
        authHeader.put("Authorization", "Basic a2FyZTpkZGUyOGM3ZWRiOWQ4MDQ2OTcyYzVjMzI3N2Q5OTlhMg==");
        RestApi restApi = RestApi.singleton();
        Call<String> api = restApi
                .setRequestHeaders(authHeader)
                .service(ApiEndpoint.class, Constanta.APP_BASE_URL)
                .payInvoiceWaStore(invoiceId, request);
        api.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        JSONObject jsonRoot = new JSONObject(response.body());
                        JSONObject jsonBody = jsonRoot.getJSONObject("body");
                    } catch (JSONException e) {
                        Timber.e(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
