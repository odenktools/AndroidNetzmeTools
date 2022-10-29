package com.odenktools.netzmeodenktools.model.qr;

import org.joda.money.Money;

/**
 * Created by wawan on 30/07/21.
 */
public class QrisTransfer {
    private String type;
    private String state;
    private String toAccountType;
    private String information;
    private String beneficiaryAccount;
    private String beneficiaryName;
    private Money feeAmount;
    private String netzmeUser;
    private String ppUrl;
    private String beneficiaryInstitution;

    public String getBeneficiaryInstitution() {
        return beneficiaryInstitution;
    }

    public void setBeneficiaryInstitution(String beneficiaryInstitution) {
        this.beneficiaryInstitution = beneficiaryInstitution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToAccountType() {
        return toAccountType;
    }

    public void setToAccountType(String toAccountType) {
        this.toAccountType = toAccountType;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public Money getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Money feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getNetzmeUser() {
        return netzmeUser;
    }

    public void setNetzmeUser(String netzmeUser) {
        this.netzmeUser = netzmeUser;
    }

    public String getPpUrl() {
        return ppUrl;
    }

    public void setPpUrl(String ppUrl) {
        this.ppUrl = ppUrl;
    }
}
