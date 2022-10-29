package com.odenktools.netzmeodenktools.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "faspay")
public class ResponseTopUpXml {
    @Element(name = "response")
    private String response = "";
    @Element(name = "trx_id")
    private String trxId = "";
    @Element(name = "merchant_id")
    private String merchantId = "";
    @Element(name = "bill_no")
    private String billNo = "";
    @Element(name = "response_code")
    private String responseCode = "";
    @Element(name = "response_desc")
    private String responseDesc = "";
    @Element(name = "response_date")
    private String responseDate = "";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }
}
