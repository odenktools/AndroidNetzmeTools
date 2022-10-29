package com.odenktools.netzmeodenktools.model;

import com.google.gson.annotations.SerializedName;

public class MerchantInvoiceBuyer {
    @SerializedName("netzme_id")
    private String netzmeId;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("desc")
    private String desc;

    @SerializedName("id")
    private String id;

    @SerializedName("masking_name")
    private boolean maskingName;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("postcode")
    private String postcode;

    @SerializedName("country")
    private String country;

    @SerializedName("user_ip")
    private String userIP;

    @SerializedName("user_agent")
    private String userAgent;

    @SerializedName("no_ktp")
    private String noKTP;

    @SerializedName("alias_id")
    private String aliasId;

    public String getNetzmeId() {
        return netzmeId;
    }

    public void setNetzmeId(String netzmeId) {
        this.netzmeId = netzmeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMaskingName() {
        return maskingName;
    }

    public void setMaskingName(boolean maskingName) {
        this.maskingName = maskingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getAliasId() {
        return aliasId;
    }

    public void setAliasId(String aliasId) {
        this.aliasId = aliasId;
    }
}
