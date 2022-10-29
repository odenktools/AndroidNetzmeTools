package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McWalletDetails {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("id_type")
    @Expose
    public String idType;

}
