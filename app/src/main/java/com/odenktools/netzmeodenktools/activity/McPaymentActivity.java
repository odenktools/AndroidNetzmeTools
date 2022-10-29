package com.odenktools.netzmeodenktools.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.odenktools.netzmeodenktools.databinding.ActivityMcPaymentBinding;
import com.odenktools.netzmeodenktools.internet.ApiEndpoint;
import com.odenktools.netzmeodenktools.internet.RestApi;
import com.odenktools.netzmeodenktools.model.mcpayment.McBillingAddress;
import com.odenktools.netzmeodenktools.model.mcpayment.McCallbackSuccessRequest;
import com.odenktools.netzmeodenktools.model.mcpayment.McCustomerDetails;
import com.odenktools.netzmeodenktools.model.mcpayment.McItemDetail;
import com.odenktools.netzmeodenktools.model.mcpayment.McPayQrisRequest;
import com.odenktools.netzmeodenktools.model.mcpayment.McPaymentDetails;
import com.odenktools.netzmeodenktools.model.mcpayment.McSelectedChannel;
import com.odenktools.netzmeodenktools.model.mcpayment.McShippingAddress;
import com.odenktools.netzmeodenktools.model.mcpayment.McWalletDetails;
import com.odenktools.netzmeodenktools.util.AppDigestUtil;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McPaymentActivity extends AppCompatActivity {

    private ActivityMcPaymentBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindSubmit();
    }

    private void bindSubmit() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitMcPayment();
                //hitMcCallback();
            }
        });
    }

    /**
     * https://developer.mcpayment.id/#8d9ee039-f999-4240-a30a-f07d5a43037c
     * <p>
     * MERCHANT_ID=MCP2022060572
     * SECRET_UNBOUND_ID=0x00e9cbcd740dbb8abd
     * HASH_KEY=CBzYsd6Tb3KKw7A
     * URL_MCPAY_STAGING=https://api-stage.mcpayment.id
     */
    public void hitMcPayment() {
        String hashKey = "CBzYsd6Tb3KKw7A";
        String externalId = "2e25ee3b-4e10-4759-8114-14faf6399353";
        //String orderId = UUID.randomUUID().toString();
        String orderId = "rsadQA5JxD9SsR2BDqN8rS";
        String hash = null;
        String mcSignature = null;
        try {
            hash = AppDigestUtil.signHash(hashKey, externalId, orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> headerHashMap = new HashMap<>();
        headerHashMap.put("x-version", "v3");
        String authToken = Credentials.basic("MCP2022060572", "0x00e9cbcd740dbb8abd");
        //String authToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjM3MzYxNDEsImlhdCI6MTY2MzczMjU0MSwiaXNzIjoiNjI2ZGJhMDNjZmE2NGQzNjllMWNjOWY2NDc5MTRhMmUiLCJyb2wiOjF9.8RkydawZO8ZVJqtUW0ZTyR_wqJBsuWgxliM9sRLNmtL5-T2rPOfJKQqgilfMsvj32lpJ83846AczXRn4ge6zPw";
        try {
            mcSignature = AppDigestUtil.signHash(hash, orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerHashMap.put("Authorization", authToken);
        headerHashMap.put("x-req-signature", hash);
        headerHashMap.put("mcp-signature", mcSignature);

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long n = 10000000L + ((long) rnd.nextInt(900000) * 100) + rnd.nextInt(100);
        String randomPhone = "0812" + n;

        McPayQrisRequest request = new McPayQrisRequest();
        request.externalId = externalId;
        request.orderId = orderId;
        request.currency = "IDR";
        request.paymentMethod = "wallet";
        request.paymentChannel = "SHOPEEPAY";
        request.additionalData = "Your additionalData Text";
        request.callbackUrl = "https://t9ww1mgdso.api.quickmocker.com/ultraman/payment/url";

        DateTime utc = new DateTime(DateTimeZone.UTC);
        DateTimeZone tz = DateTimeZone.forID("Asia/Jakarta");
        DateTime jakartaTime = utc.toDateTime(tz);

        McPaymentDetails paymentDetails = new McPaymentDetails();
        paymentDetails.amount = 2000;
        paymentDetails.expiredTime = jakartaTime.plusHours(1).toDateTimeISO().toString();
        paymentDetails.transactionDescription = "Ini adalah percobaan rahyan";
        paymentDetails.isCustomerPayingFee = false;
        request.paymentDetails = paymentDetails;

        McCustomerDetails customerDetails = new McCustomerDetails();
        customerDetails.email = "customerz@email.com";
        customerDetails.fullName = "Your Customer Name";
        customerDetails.phone = randomPhone;
        request.customerDetails = customerDetails;

        List<McItemDetail> itemDetailList = new ArrayList<>();
        McItemDetail itemDetail = new McItemDetail();
        String cutString = UUID.randomUUID().toString().substring(0, 25);
        itemDetail.itemId = cutString;
        itemDetail.amount = 2000;
        itemDetail.name = "Barang bagus";
        itemDetail.qty = 1;
        itemDetail.description = "ini barang bagus";
        itemDetailList.add(itemDetail);
        request.itemDetails = itemDetailList;

        McWalletDetails walletDetails = new McWalletDetails();
        walletDetails.id = randomPhone;
        walletDetails.idType = "HP";
        request.walletDetails = walletDetails;


        McBillingAddress billingAddress = new McBillingAddress();
        billingAddress.address = "Your Billing Name";
        billingAddress.fullName = "Alamat saya";
        billingAddress.city = "Jakarta";
        billingAddress.postalCode = "12345";
        billingAddress.country = "Indonesia";
        request.billingAddress = billingAddress;

        McShippingAddress shippingAddress = new McShippingAddress();
        shippingAddress.phone = randomPhone;
        shippingAddress.address = "Alamat saya Shipping";
        shippingAddress.city = "Belitung";
        shippingAddress.postalCode = "12346";
        shippingAddress.country = "ID";
        shippingAddress.fullName = "billing customer fullname";
        request.shippingAddress = shippingAddress;

        RestApi restApi = RestApi.singleton();
        Call<String> req = restApi
                .setRequestHeaders(headerHashMap)
                .service(ApiEndpoint.class, "https://api-stage.mcpayment.id/ewallet/v2")
                .postMcPayCreateQris(request);
        req.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    binding.editTextTextMultiLine.setText(response.body().toString());
                } else {
                    //binding.editTextTextMultiLine.setText(response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.editTextTextMultiLine.setText(t.getMessage().toString());
            }
        });
    }

    public void hitMcCallback() {
        String hashKey = "CBzYsd6Tb3KKw7A";
        String externalId = "2e25ee3b-4e10-4759-8114-14faf6399353";
        //String orderId = UUID.randomUUID().toString();
        String orderId = "rsadQA5JxD9SsR2BDqN8rS";
        String hash = null;
        String mcSignature = null;
        try {
            hash = AppDigestUtil.signHash(hashKey, externalId, orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> headerHashMap = new HashMap<>();
        headerHashMap.put("x-version", "v3");
        String authToken = Credentials.basic("MCP2022060572", "0x00e9cbcd740dbb8abd");
        //String authToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjM3MzYxNDEsImlhdCI6MTY2MzczMjU0MSwiaXNzIjoiNjI2ZGJhMDNjZmE2NGQzNjllMWNjOWY2NDc5MTRhMmUiLCJyb2wiOjF9.8RkydawZO8ZVJqtUW0ZTyR_wqJBsuWgxliM9sRLNmtL5-T2rPOfJKQqgilfMsvj32lpJ83846AczXRn4ge6zPw";
        try {
            mcSignature = AppDigestUtil.signHash(hash, orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerHashMap.put("Authorization", authToken);
        headerHashMap.put("x-req-signature", hash);
        headerHashMap.put("mcp-signature", mcSignature);

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        long n = 10000000L + ((long) rnd.nextInt(900000) * 100) + rnd.nextInt(100);
        String randomPhone = "0812" + n;

        McCallbackSuccessRequest request = new McCallbackSuccessRequest();
        request.externalId = externalId;
        request.orderId = orderId;
        request.currency = "IDR";
        request.paymentMethod = "CARD";
        request.paymentChannel = "CARD";
        request.additionalData = "Your additionalData Text";
        request.callbackUrl = "https://t9ww1mgdso.api.quickmocker.com/ultraman/payment/url";

        DateTime utc = new DateTime(DateTimeZone.UTC);
        DateTimeZone tz = DateTimeZone.forID("Asia/Jakarta");
        DateTime jakartaTime = utc.toDateTime(tz);

        McPaymentDetails paymentDetails = new McPaymentDetails();
        paymentDetails.amount = 1000;
        paymentDetails.expiredTime = jakartaTime.plusHours(1).toDateTimeISO().toString();
        paymentDetails.transactionDescription = "";
        paymentDetails.isCustomerPayingFee = false;
        request.paymentDetails = paymentDetails;

        McCustomerDetails customerDetails = new McCustomerDetails();
        customerDetails.email = "customerz@email.com";
        customerDetails.fullName = "Your Customer Name";
        customerDetails.phone = randomPhone;
        customerDetails.phone = "081111111111";
        customerDetails.address = "Jalan Ancol Damai nomor 51, Jakarta Utara";
        request.customerDetails = customerDetails;

        List<McItemDetail> itemDetailList = new ArrayList<>();
        McItemDetail itemDetail = new McItemDetail();
        itemDetail.itemId = UUID.randomUUID().toString();
        itemDetail.amount = 10000;
        itemDetail.name = "item-1";
        itemDetail.qty = 6;
        itemDetail.name = "Merchant item name";
        itemDetailList.add(itemDetail);
        request.itemDetails = itemDetailList;

        McWalletDetails walletDetails = new McWalletDetails();
        walletDetails.id = randomPhone;
        walletDetails.idType = "HP";
        //request.walletDetails = walletDetails;

        List<McSelectedChannel> selectedChannelList = new ArrayList<>();
        McSelectedChannel selectedChannel = new McSelectedChannel();
        selectedChannel.acq = "DANA";
        selectedChannel.channel = "DANA";
        selectedChannelList.add(selectedChannel);
        request.selectedChannelList = selectedChannelList;

        McBillingAddress billingAddress = new McBillingAddress();
        billingAddress.address = "Your Billing Name";
        billingAddress.fullName = "Alamat saya";
        billingAddress.city = "Jakarta";
        billingAddress.postalCode = "12345";
        billingAddress.country = "Indonesia";
        request.billingAddress = billingAddress;

        request.isCustomerPayingFee = false;

        McShippingAddress shippingAddress = new McShippingAddress();
        //shippingAddress.phone = randomPhone;
        shippingAddress.phone = "628888888888";
        shippingAddress.address = "Alamat saya Shipping";
        shippingAddress.city = "Belitung";
        shippingAddress.postalCode = "12346";
        shippingAddress.country = "ID";
        shippingAddress.fullName = "billing customer fullname";
        request.shippingAddress = shippingAddress;
        request.acq = "acquirer";
        request.saveCard = false;

        request.successRedirectUrl = "https://t9ww1mgdso.api.quickmocker.com/ultraman/payment/success";
        request.failedRedirectUrl = "https://t9ww1mgdso.api.quickmocker.com/ultraman/payment/failed";
        request.callbackUrl = "https://t9ww1mgdso.api.quickmocker.com/ultraman/payment/url";

        RestApi restApi = RestApi.singleton();
        //restApi.setBaseUrl("https://api-stage.mcpayment.id/ewallet/v2/");
        Call<String> req = restApi
                .setRequestHeaders(headerHashMap)
                .service(ApiEndpoint.class, "http://external-api.com/")
                .postMcPayCreateQris(request);
        req.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    binding.editTextTextMultiLine.setText(response.body().toString());
                } else {
                    //binding.editTextTextMultiLine.setText(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                binding.editTextTextMultiLine.setText(t.getMessage().toString());
            }
        });
    }
}