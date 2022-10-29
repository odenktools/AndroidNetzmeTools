package com.odenktools.netzmeodenktools.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.odenktools.netzmeodenktools.databinding.ActivityTopUpBinding;
import com.odenktools.netzmeodenktools.internet.ApiEndpoint;
import com.odenktools.netzmeodenktools.internet.RestApiXml;
import com.odenktools.netzmeodenktools.model.InquiryBcaXML;
import com.odenktools.netzmeodenktools.model.ResponseTopUpXml;
import com.odenktools.netzmeodenktools.model.request.RequestTopUpXml;
import com.odenktools.netzmeodenktools.util.AppUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TopUpActivity extends AppCompatActivity {

    private ActivityTopUpBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fireButtonAction();
    }

    private void fireButtonAction() {
        binding.btnTopup.setOnClickListener(v -> {
            //send
            sendTopUpBackend(binding.edVaNumber.getText().toString(), binding.edNominal.getText().toString());
            //sendTopUpBackend("HPWUtQRz", "100000");
        });

        binding.btnInquiry.setOnClickListener(v -> {
            //send
            //sendTopUpBackend(binding.edVaNumber.getText().toString(), binding.edNominal.getText().toString());
            //sendTopUpBackend("HPWUtQRz", "100000");
            //sendInquiryBca(binding.edVaNumber.getText().toString(), binding.edNominal.getText().toString());
            sendInquiryBca("11381085363542775", "100000");
        });
    }

    private void sendInquiryBca(String vaNumber, String nominal) {
        final String randomUUID = UUID.randomUUID().toString();
        final String signInquiry = AppUtils.signTopup("bot31922" + "p@ssw0rd" + vaNumber + "2");
        Call<InquiryBcaXML> callData = RestApiXml
                .singleton()
                .service(ApiEndpoint.class)
                .inquiry(vaNumber,
                        signInquiry,
                        vaNumber,
                        "payment",
                        randomUUID,
                        nominal,
                        signInquiry);

        callData.enqueue(new Callback<InquiryBcaXML>() {
            @Override
            public void onResponse(Call<InquiryBcaXML> call, Response<InquiryBcaXML> response) {
                if (response.isSuccessful()) {
                    InquiryBcaXML res = response.body();
                    if (StringUtils.equals(res.getResponseCode(), "00")) {
                        AppUtils.showSnackBar(binding.getRoot(),
                                TopUpActivity.this, "Inquiry Success");
                    } else {
                        AppUtils.showFailedSnackBar(binding.getRoot(),
                                TopUpActivity.this, "Inquiry Gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<InquiryBcaXML> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Inquiry Gagal", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void sendTopUpBackend(String vaNumber, String nominal) {
        String randomUUID = UUID.randomUUID().toString();
        String signature = AppUtils.signTopup("bot31922" + "p@ssw0rd" + vaNumber + "2");
        RequestTopUpXml requestXml = new RequestTopUpXml();
        requestXml.setRequest("Payment Notification");
        requestXml.setTrx_id(randomUUID);
        requestXml.setMerchant_id("BCA");
        requestXml.setMerchant("BCA");
        requestXml.setBill_no(vaNumber);
        requestXml.setPayment_reff(null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        requestXml.setPayment_date(currentDateandTime);
        requestXml.setPayment_status_code("2");
        requestXml.setPayment_status_desc("Success");
        requestXml.setAmount(vaNumber);
        requestXml.setSignature(signature);
        requestXml.setPayment_total(nominal);
        Call<ResponseTopUpXml> callData = RestApiXml
                .singleton()
                .service(ApiEndpoint.class)
                .paymentNotification(requestXml);

        callData.enqueue(new Callback<ResponseTopUpXml>() {
            @Override
            public void onResponse(Call<ResponseTopUpXml> call, Response<ResponseTopUpXml> response) {
                if (response.isSuccessful()) {
                    ResponseTopUpXml body = response.body();
                    if (StringUtils.equals(body.getResponseCode(), "00")) {

                    }
                    Timber.d("DATAAAAAAAAAA %s", body.getResponseDesc());
                }
            }

            @Override
            public void onFailure(Call<ResponseTopUpXml> call, Throwable t) {

            }
        });
    }
}
