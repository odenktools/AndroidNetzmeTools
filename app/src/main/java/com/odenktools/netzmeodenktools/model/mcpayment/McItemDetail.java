package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McItemDetail {

    @SerializedName("item_id")
    @Expose
    public String itemId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("amount")
    @Expose
    public Integer amount;
    @SerializedName("qty")
    @Expose
    public Integer qty;
    @SerializedName("description")
    @Expose
    public String description;

}
