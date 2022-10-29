package com.odenktools.netzmeodenktools.util;

import org.joda.money.Money;

import java.util.UUID;

public class Constanta {
    public static final String KARE_USER = "kare";
    public static final String KARE_PASSWORD = "dde28c7edb9d8046972c5c3277d999a2";
    public static final String RND_UUID = UUID.randomUUID().toString();
    public static final Money MONEY_TO_PAY = Money.parse("IDR 2000");
    public static final Money MONEY_TO_PAY_HUNDRED = Money.parse("IDR 10000");
    public static final Money MONEY_ZERO = Money.parse("IDR 0");
    public static final String FEE_ON_BUYER = "on_buyer";
    public static final String FEE_ON_SELLER = "on_seller";
    public static final String WA_STORE_INVOICE = "create_wastore_invoice";
    public static final String WA_STORE_INVOICE_TYPE_SINGLE = "single";
    public static final String WA_STORE_INVOICE_TYPE_MULTI = "multiple";
    public static final Integer EXPIRED_ONE_HOUR = 3600;
    public static final Integer EXPIRED_THREE_MINUTES = 180;

    public static String INVOICE_ID = "";
    public static String TRX_ID = "";
    public static String CUST_PHONE = "";
    public static String CUST_EMAIL = "";
    public static String CUST_ID = "";

    public static String APP_BASE_URL = "https://tokoapi-dev.netzme.com/";
    public static String CUSTOMER_TABLE = "customers";

}
