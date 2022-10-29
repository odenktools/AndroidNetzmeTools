package com.odenktools.netzmeodenktools.model.qr;

import org.joda.money.Money;

import java.util.HashMap;

/**
 * Created by wawansetiawan on 11/13/19.
 */
public class QRis extends EMVco {
    public static final String STATUS_REQUESTED = "requested";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_DEBT = "debt";
    public static final String STATUS_BALANCE = "balance";
    public static final String STATUS_SUSPEND = "suspend";
    //if check status response expect both 00 & A0, status will back to intial state
    public static final String STATUS_INTIAL_STATE = "initial_state";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_SETTLE = "settle";
    public static final String STATUS_UNSETTLE = "unsettle";
    public static final String STATUS_NOT_ENOUGH_BALANCE = "not_enough_balance";

    public static final String TIPS_WITHOUT_AMOUNT_TYPE = "01";
    public static final String TIPS_FIXED_AMOUNT_TYPE = "02";
    public static final String TIPS_PERCENTAGE_AMOUNT_TYPe ="03";

    private HashMap<String, QRisMerchantInfo> merchantInfo;
    private String merchantCategoryCode;
    private QRisAdditionalData additionalData;
    private Money discount;
    private String convenienceFlag = "C";

    public QRisAdditionalData getAdditionalData() {
        return additionalData;
    }

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        this.discount = discount;
    }

    public String getConvenienceFlag() {
        return convenienceFlag;
    }

    public void setConvenienceFlag(String convenienceFlag) {
        this.convenienceFlag = convenienceFlag;
    }

    public void setAdditionalData(QRisAdditionalData additionalData) {
        this.additionalData = additionalData;
    }

    public HashMap<String, QRisMerchantInfo> getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(HashMap<String, QRisMerchantInfo> merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

}
