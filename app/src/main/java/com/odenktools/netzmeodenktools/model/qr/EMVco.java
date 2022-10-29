package com.odenktools.netzmeodenktools.model.qr;

import org.joda.money.Money;

/**
 * Created by wawansetiawan on 11/13/19.
 */
public class EMVco {
    private String payloadFormatIndicator;
    private String pointOfInitiation;
    private String transactionCurrency;
    private Money transactionAmount;
    private String tipConvenience;
    private Money valueConvenienceFeeFixed;
    private Double valueConvenienceFeePercentage;
    private Money feeAmount;
    private String countryCode;
    private String merchantName;
    private String merchantCity;
    private String postalCode;
    private String crc;

    public Money getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Money feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getPayloadFormatIndicator() {
        return payloadFormatIndicator;
    }

    public void setPayloadFormatIndicator(String payloadFormatIndicator) {
        this.payloadFormatIndicator = payloadFormatIndicator;
    }

    public String getPointOfInitiation() {
        return pointOfInitiation;
    }

    public void setPointOfInitiation(String pointOfInitiation) {
        this.pointOfInitiation = pointOfInitiation;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public Money getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Money transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTipConvenience() {
        return tipConvenience;
    }

    public void setTipConvenience(String tipConvenience) {
        this.tipConvenience = tipConvenience;
    }

    public Money getValueConvenienceFeeFixed() {
        return valueConvenienceFeeFixed;
    }

    public void setValueConvenienceFeeFixed(Money valueConvenienceFeeFixed) {
        this.valueConvenienceFeeFixed = valueConvenienceFeeFixed;
    }

    public Double getValueConvenienceFeePercentage() {
        return valueConvenienceFeePercentage;
    }

    public void setValueConvenienceFeePercentage(Double valueConvenienceFeePercentage) {
        this.valueConvenienceFeePercentage = valueConvenienceFeePercentage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }
}
