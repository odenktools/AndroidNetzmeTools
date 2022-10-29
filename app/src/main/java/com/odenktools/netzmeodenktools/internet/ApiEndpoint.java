package com.odenktools.netzmeodenktools.internet;

import com.odenktools.netzmeodenktools.model.InquiryBcaXML;
import com.odenktools.netzmeodenktools.model.ResponseTopUpXml;
import com.odenktools.netzmeodenktools.model.mcpayment.McCallbackSuccessRequest;
import com.odenktools.netzmeodenktools.model.mcpayment.McPayQrisRequest;
import com.odenktools.netzmeodenktools.model.request.InvoiceWaStoreRequest;
import com.odenktools.netzmeodenktools.model.request.MerchantPayInvoiceRequest;
import com.odenktools.netzmeodenktools.model.request.QrPaymentRequest;
import com.odenktools.netzmeodenktools.model.request.RequestTopUpXml;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {

    /**
     * Untuk membayar QRIS melalui backend.
     */
    @POST("qr/payment")
    Call<String> postData(@Body QrPaymentRequest request);

    /**
     * Membuat invoice.
     */
    @POST("api/merchant/invoice/wastore")
    Call<String> createInvoiceWaStore(@Body InvoiceWaStoreRequest request);

    /**
     * Membuat invoice.
     */
    @POST("api/merchant/invoice/{invoice_id}/pay")
    Call<String> payInvoiceWaStore(@Path("invoice_id") String invoiceId,
                                   @Body MerchantPayInvoiceRequest request);

    @GET("faspay/{va}/{hash_va}")
    Call<InquiryBcaXML> inquiry(@Path("va") String va,
                                @Path("hash_va") String hash_va,
                                @Query("va") String vaQuery,
                                @Query("type") String type,
                                @Query("trx_uid") String trxUid,
                                @Query("amount") String amount,
                                @Query("sign") String hashVa);

    /**
     * Topup VA BCA
     */
    @POST("fp/payment/notification")
    Call<ResponseTopUpXml> paymentNotification(@Body RequestTopUpXml request);

    /**
     * Mc payment create QRIS
     */
    @POST("create-qr")
    Call<String> postMcPayCreateQris(@Body McPayQrisRequest body);

    /**
     * MC Payment callback
     */
    @POST("payment/callback-success")
    Call<String> postMcPayCreateQris(@Body McCallbackSuccessRequest body);
}
