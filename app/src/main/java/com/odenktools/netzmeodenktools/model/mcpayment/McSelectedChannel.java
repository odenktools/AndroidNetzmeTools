package com.odenktools.netzmeodenktools.model.mcpayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McSelectedChannel {

    @SerializedName("acq")
    @Expose
    public String acq;

    @SerializedName("channel")
    @Expose
    public String channel;
}
