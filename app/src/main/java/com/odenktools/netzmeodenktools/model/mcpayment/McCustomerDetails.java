package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McCustomerDetails {

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("full_name")
    @Expose
    public String fullName;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("address")
    @Expose
    public String address;
}
