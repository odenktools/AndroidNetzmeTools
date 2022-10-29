package com.odenktools.netzmeodenktools.model.qr;

/**
 * Created by ahmadulil on 6/13/17.
 */
public class GenericError {
    
    public static final int DATABASE_ERROR = 1;
    public static final int EXTERNAL_SERVICE_ERROR = 2;
    public static final int UNKNOWN_ERROR = 3;
    public static final int INVALID_TOKEN = 4;
    public static final int ACCOUNT_BALANCE_LIMIT = 5;
    public static final int INVALID_REQUEST = 6;

    private int code;
    private String description;

    public GenericError() {
    }

    public GenericError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
