package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McPaymentDetails {

    @SerializedName("billing_name")
    @Expose
    public String billingName;

    @SerializedName("amount")
    @Expose
    public Integer amount;

    @SerializedName("payment_system")
    @Expose
    public String paymentSystem = "CLOSED";

    @SerializedName("va_number")
    @Expose
    public String vaNumber;

    @SerializedName("is_multi_use")
    @Expose
    public Boolean is_multi_use = false;


    @SerializedName("expired_time")
    @Expose
    public String expiredTime;

    @SerializedName("transaction_description")
    @Expose
    public String transactionDescription;

    @SerializedName("is_customer_paying_fee")
    @Expose
    public Boolean isCustomerPayingFee = false;
}
