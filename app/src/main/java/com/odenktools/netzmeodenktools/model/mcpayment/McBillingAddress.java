package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McBillingAddress {

    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("postal_code")
    @Expose
    public String postalCode;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("full_name")
    @Expose
    public String fullName;

}
