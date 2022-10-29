package com.odenktools.netzmeodenktools.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "faspay")
public class InquiryBcaXML {
    @Element(name = "response")
    private String response = "";

    @Element(name = "va_number")
    private String vaNumber = "";

    @Element(name = "amount")
    private String amount = "";

    @Element(name = "cust_name")
    private String custName = "";

    @Element(name = "response_code")
    private String responseCode = "";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getVaNumber() {
        return vaNumber;
    }

    public void setVaNumber(String vaNumber) {
        this.vaNumber = vaNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
