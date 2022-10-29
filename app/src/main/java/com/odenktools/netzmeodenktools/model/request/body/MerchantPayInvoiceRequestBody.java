package com.odenktools.netzmeodenktools.model.request.body;

import com.google.gson.annotations.SerializedName;
import com.odenktools.netzmeodenktools.model.MerchantInvoiceBuyer;

import org.joda.money.Money;

public class MerchantPayInvoiceRequestBody {

    @SerializedName("buyer")
    private MerchantInvoiceBuyer buyer;
    private String invoiceId;
    private String invoiceTrxId;
    private Money paidAmount;
    private Money amount;
    private Money feeAmount;
    private String feeType;

    public MerchantInvoiceBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(MerchantInvoiceBuyer buyer) {
        this.buyer = buyer;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceTrxId() {
        return invoiceTrxId;
    }

    public void setInvoiceTrxId(String invoiceTrxId) {
        this.invoiceTrxId = invoiceTrxId;
    }

    public Money getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Money paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Money getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Money feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
}
