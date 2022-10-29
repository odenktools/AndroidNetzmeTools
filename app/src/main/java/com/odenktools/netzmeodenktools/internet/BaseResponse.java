package com.odenktools.netzmeodenktools.internet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class sebagai root response.
 *
 * @author Odenktools
 */
public class BaseResponse<T> {

    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;

    @SerializedName("data")
    @Expose
    private T dataResponse = null;
}
