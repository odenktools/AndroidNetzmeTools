package com.odenktools.netzmeodenktools.model.request.body;

import com.google.gson.annotations.SerializedName;
import com.odenktools.netzmeodenktools.util.Constanta;

import java.util.ArrayList;
import java.util.List;

public class InvoiceWaStoreRequestBody {
    public String trxSource;

    public String merchantId;

    public String descFromMerchant;

    public String descFromBuyer;

    public String fullNameBuyer;

    public String emailBuyer;

    public String phoneNoBuyer;

    public String callbackUrl;

    public String invoicePurpose;

    public String feeType = Constanta.FEE_ON_BUYER;

    public String invoiceType;

    public String urlPhoto;

    public Integer expireInSecond = Constanta.EXPIRED_ONE_HOUR;

    public Integer commissionPercentage = 0;

    @SerializedName("amount")
    public PayAmount payAmount;

    public List<String> paymentMethods = new ArrayList<>();
}
