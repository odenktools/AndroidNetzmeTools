package com.odenktools.netzmeodenktools.model.qr;

/**
 * Created by wawansetiawan on 11/13/19.
 */
public class QRisMerchantInfo {
    private String globalUniqueIndentifier;
    private String merchantPan;
    private String merchantId;
    private String merchantCriteria;

    public String getGlobalUniqueIndentifier() {
        return globalUniqueIndentifier;
    }

    public void setGlobalUniqueIndentifier(String globalUniqueIndentifier) {
        this.globalUniqueIndentifier = globalUniqueIndentifier;
    }

    public String getMerchantPan() {
        return merchantPan;
    }

    public void setMerchantPan(String merchantPan) {
        this.merchantPan = merchantPan;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCriteria() {
        return merchantCriteria;
    }

    public void setMerchantCriteria(String merchantCriteria) {
        this.merchantCriteria = merchantCriteria;
    }
}
