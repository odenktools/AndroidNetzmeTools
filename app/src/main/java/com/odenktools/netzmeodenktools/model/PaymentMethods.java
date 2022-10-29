package com.odenktools.netzmeodenktools.model;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethods {
    public static final String BCA = "BCA";
    public static final String BANK_TRANSFER = "BANK_TRANSFER";
    public static final String NETZME = "NETZME";
    public static final String RETAIL_OUTLET = "RETAIL_OUTLET";
    public static final String QRIS = "QRIS";

    public static List<String> addAllPaymentMethod() {
        List<String> addPayment = new ArrayList<>();
        addPayment.add(BCA);
        addPayment.add(BANK_TRANSFER);
        addPayment.add(NETZME);
        addPayment.add(RETAIL_OUTLET);
        addPayment.add(QRIS);
        return addPayment;
    }

    public static List<String> addBankPaymentMethod() {
        List<String> addPayment = new ArrayList<>();
        addPayment.add(BCA);
        addPayment.add(BANK_TRANSFER);
        return addPayment;
    }

    public static List<String> addQrisPaymentMethod() {
        List<String> addPayment = new ArrayList<>();
        addPayment.add(QRIS);
        return addPayment;
    }

    public static List<String> addRetailPaymentMethod() {
        List<String> addPayment = new ArrayList<>();
        addPayment.add(RETAIL_OUTLET);
        return addPayment;
    }

    public static List<String> addNetzmePaymentMethod() {
        List<String> addPayment = new ArrayList<>();
        addPayment.add(NETZME);
        return addPayment;
    }
}
