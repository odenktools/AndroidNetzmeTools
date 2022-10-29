package com.odenktools.netzmeodenktools.model.db;

import com.google.gson.annotations.SerializedName;

public class CustomerDb {

    private String id;

    @SerializedName("customer_phone")
    private String customerPhone;

    @SerializedName("customer_email")
    private String customerEmail;

    @SerializedName("netzme_id")
    private String netzmeId;

    @SerializedName("customer_full_name")
    private String customerFullName;

    @SerializedName("customer_address")
    private String customerAddress;

    @SerializedName("customer_is_active")
    private Integer isActive = 0;

    public CustomerDb() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getNetzmeId() {
        return netzmeId;
    }

    public void setNetzmeId(String netzmeId) {
        this.netzmeId = netzmeId;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
