package com.odenktools.netzmeodenktools.internet;

import com.odenktools.netzmeodenktools.model.*;
import com.odenktools.netzmeodenktools.model.request.*;

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
}
