package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class McPayQrisRequest {

    @SerializedName("external_id")
    @Expose
    public String externalId;

    @SerializedName("order_id")
    @Expose
    public String orderId;

    @SerializedName("currency")
    @Expose
    public String currency;

    @SerializedName("payment_method")
    @Expose
    public String paymentMethod;

    @SerializedName("payment_channel")
    @Expose
    public String paymentChannel;

    @SerializedName("payment_details")
    @Expose
    public McPaymentDetails paymentDetails;

    @SerializedName("customer_details")
    @Expose
    public McCustomerDetails customerDetails;

    @SerializedName("item_details")
    @Expose
    public List<McItemDetail> itemDetails = null;

    @SerializedName("wallet_details")
    @Expose
    public McWalletDetails walletDetails;

    @SerializedName("billing_address")
    @Expose
    public McBillingAddress billingAddress;

    @SerializedName("shipping_address")
    @Expose
    public McShippingAddress shippingAddress;

    @SerializedName("additional_data")
    @Expose
    public String additionalData;

    @SerializedName("callback_url")
    @Expose
    public String callbackUrl;
}
