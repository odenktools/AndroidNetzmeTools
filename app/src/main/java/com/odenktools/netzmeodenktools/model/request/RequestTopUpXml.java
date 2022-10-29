package com.odenktools.netzmeodenktools.model.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "faspay")
public class RequestTopUpXml {

    @Element(name = "request")
    private String request = "";

    @Element(name = "trx_id")
    private String trx_id = "";

    @Element(name = "merchant_id")
    private String merchant_id = "";

    @Element(name = "merchant")
    private String merchant = "";

    @Element(name = "bill_no")
    private String bill_no = "";

    @Element(name = "payment_reff", required = false)
    private String payment_reff = null;


    @Element(name = "payment_date")
    private String payment_date = "";

    @Element(name = "payment_status_code")
    private String payment_status_code = "";


    @Element(name = "payment_status_desc")
    private String payment_status_desc = "";


    @Element(name = "amount")
    private String amount = "";


    @Element(name = "signature")
    private String signature = "";

    @Element(name = "payment_total")
    private String payment_total = "";

    public String getRequest() {
        return request;
    }

    public RequestTopUpXml() {
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getTrx_id() {
        return trx_id;
    }

    public void setTrx_id(String trx_id) {
        this.trx_id = trx_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getPayment_reff() {
        return payment_reff;
    }

    public void setPayment_reff(String payment_reff) {
        this.payment_reff = payment_reff;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_status_code() {
        return payment_status_code;
    }

    public void setPayment_status_code(String payment_status_code) {
        this.payment_status_code = payment_status_code;
    }

    public String getPayment_status_desc() {
        return payment_status_desc;
    }

    public void setPayment_status_desc(String payment_status_desc) {
        this.payment_status_desc = payment_status_desc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPayment_total() {
        return payment_total;
    }

    public void setPayment_total(String payment_total) {
        this.payment_total = payment_total;
    }
}
