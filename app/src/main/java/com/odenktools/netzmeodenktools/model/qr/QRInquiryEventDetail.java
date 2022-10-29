package com.odenktools.netzmeodenktools.model.qr;

import org.joda.money.Money;

/**
 * Created by nanda on 11/17/16.
 */
public class QRInquiryEventDetail {

    private String qrContent;
    private String qrType;
    private String errorMessage;
    private String status;

    private String invoiceId;
    private String merchantApiKey;
    private String merchantName;
    private Money invoiceAmount;
    private Money payableAmount;
    private double discount;
    private String refId;
    private ScanToPayDetail scanToPayDetail;
    private QRis qRisDetail;
    private QrisTransfer qrisTransfer;
    private String inquiryType;

    public QrisTransfer getQrisTransfer() {
        return qrisTransfer;
    }

    public void setQrisTransfer(QrisTransfer qrisTransfer) {
        this.qrisTransfer = qrisTransfer;
    }

    public QRis getqRisDetail() {
        return qRisDetail;
    }

    public void setqRisDetail(QRis qRisDetail) {
        this.qRisDetail = qRisDetail;
    }

    private ErrorMessage error;

    public String getQrContent() {
        return qrContent;
    }

    public void setQrContent(String qrContent) {
        this.qrContent = qrContent;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMerchantApiKey() {
        return merchantApiKey;
    }

    public void setMerchantApiKey(String merchantApiKey) {
        this.merchantApiKey = merchantApiKey;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Money getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Money invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Money getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(Money payableAmount) {
        this.payableAmount = payableAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ScanToPayDetail getScanToPayDetail() {
        return scanToPayDetail;
    }

    public void setScanToPayDetail(ScanToPayDetail scanToPayDetail) {
        this.scanToPayDetail = scanToPayDetail;
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }
}
