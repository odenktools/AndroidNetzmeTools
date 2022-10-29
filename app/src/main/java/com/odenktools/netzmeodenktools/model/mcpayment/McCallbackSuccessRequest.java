package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class McCallbackSuccessRequest {

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

    @SerializedName("selected_channels")
    @Expose
    public List<McSelectedChannel> selectedChannelList;

    @SerializedName("shipping_address")
    @Expose
    public McShippingAddress shippingAddress;

    @SerializedName("additional_data")
    @Expose
    public String additionalData;

    @SerializedName("callback_url")
    @Expose
    public String callbackUrl;

    @SerializedName("paid_date")
    @Expose
    public String paidDate;

    @SerializedName("acq")
    @Expose
    public String acq;

    @SerializedName("is_customer_paying_fee")
    @Expose
    public Boolean isCustomerPayingFee;

    @SerializedName("save_card")
    @Expose
    public Boolean saveCard;


    @SerializedName("success_redirect_url")
    @Expose
    public String successRedirectUrl;

    @SerializedName("failed_redirect_url")
    @Expose
    public String failedRedirectUrl;
}
