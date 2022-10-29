package com.odenktools.netzmeodenktools.util;

import org.joda.money.Money;

import java.util.HashMap;

public class QRTLVParser {

    public static final int SIZE_OF_TAG = 2;
    public static final int SIZE_OF_LENGTH = 2;

    public static final String TAG_PAYLOAD_FORMAT_INDICATOR = "00";
    public static final String TAG_POINT_OF_INITIATION = "01";
    public static final String TAG_VISA_PAN = "02";
    public static final String TAG_MASTERCARD_PAN = "04";
    public static final String TAG_MERCHANT_INFO_26 = "26";
    public static final String TAG_MERCHANT_INFO_27 = "27";
    public static final String TAG_MERCHANT_INFO_28 = "28";
    public static final String TAG_MERCHANT_INFO_29 = "29";
    public static final String TAG_MERCHANT_INFO_30 = "30";
    public static final String TAG_MERCHANT_INFO_31 = "31";
    public static final String TAG_MERCHANT_INFO_32 = "32";
    public static final String TAG_MERCHANT_INFO_33 = "33";
    public static final String TAG_MERCHANT_INFO_34 = "34";
    public static final String TAG_MERCHANT_INFO_35 = "35";
    public static final String TAG_MERCHANT_INFO_36 = "36";
    public static final String TAG_MERCHANT_INFO_37 = "37";
    public static final String TAG_MERCHANT_INFO_38 = "38";
    public static final String TAG_MERCHANT_INFO_39 = "39";
    public static final String TAG_MERCHANT_INFO_40 = "40";
    public static final String TAG_MERCHANT_INFO_41 = "41";
    public static final String TAG_MERCHANT_INFO_42 = "42";
    public static final String TAG_MERCHANT_INFO_43 = "43";
    public static final String TAG_MERCHANT_INFO_44 = "44";
    public static final String TAG_MERCHANT_INFO_45 = "45";

    public static final String TAG_EMONEY_PAN = "33";
    public static final String TAG_MERCHANT_ID = "35";
    public static final String TAG_MERCHANT_INFO_51 = "51";
    public static final String TAG_MERCHANT_CATEGORY = "52";
    public static final String TAG_TRANSACTION_CURRENCY = "53";
    public static final String TAG_TRANSACTION_AMOUNT = "54";
    public static final String TAG_TIP_CONVENIENCE = "55";
    public static final String TAG_VALUE_OF_CONVENIENCE_FIXED_FEE = "56";
    public static final String TAG_VALUE_OF_CONVENIENCE_PERCENTAGE_FEE = "57";
    public static final String TAG_COUNTRY_CODE = "58";
    public static final String TAG_MERCHANT_NAME = "59";
    public static final String TAG_MERCHANT_CITY = "60";
    public static final String TAG_POSTAL_CODE = "61";
    public static final String TAG_ADDITIONAL_DATA = "62";
    public static final String TAG_ADDITIONAL_DATA_SUB_PROPRIETARY = "99";
    public static final String TAG_ADDITIONAL_DATA_SUB_PROPRIETARY_SUB_00 = "00";
    public static final String TAG_ADDITIONAL_DATA_SUB_PROPRIETARY_SUB_01 = "01";

    public static final String TAG_CRC = "63";

    public static final String SUB_TAG_BILL_ID = "01";
    public static final String SUB_TAG_REFERENCE_ID = "05";
    public static final String SUB_TAG_TERMINAL_ID = "07";

    public static final String SUB_TAG_GLOBAL_UNIQUE_IDENTIFIER = "00";
    public static final String SUB_TAG_MERCHANT_PAN = "01";
    public static final String SUB_TAG_MERCHANT_ID = "02";
    public static final String SUB_TAG_MERCHANT_CRITERIA = "03";

    public static final String SUB_TAG_BILL_NUMBER = "01";
    public static final String SUB_TAG_MOBILE_NUMBER = "02";
    public static final String SUB_TAG_STORE_LABEL = "03";
    public static final String SUB_TAG_LOYALTY_NUMBER = "04";
    public static final String SUB_TAG_REFERENCE_LABEL = "05";
    public static final String SUB_TAG_CUSTOMER_LABEL = "06";
    public static final String SUB_TAG_TERMINAL_LABEL = "07";
    public static final String SUB_TAG_PURPOSE_OF_TRANSACTION = "08";


    public static final String TIP_INDICATOR_OPEN = "01";
    public static final String TIP_INDICATOR_FIX = "02";
    public static final String TIP_INDICATOR_PROSENTASE = "03";

    public static final String DEBET = "D";
    public static final String CREDIT = "C";

    public static HashMap<String, String> parseQR(final String qrString) throws Exception {
        final HashMap<String, String> ret = new HashMap<>();
        int cursor = 0;
        final int length = qrString.length();

        while (cursor < length) {
            final String tag = qrString.substring(cursor, cursor + SIZE_OF_TAG);
            cursor = cursor + SIZE_OF_TAG;
            final int lengthTag = Integer.valueOf(qrString.substring(cursor, cursor + SIZE_OF_LENGTH));
            cursor = cursor + SIZE_OF_LENGTH;
            final String value = qrString.substring(cursor, cursor + lengthTag);
            cursor = cursor + lengthTag;
            ret.put(tag, value);
        }
        return ret;
    }

    public static Money getMoney(final String value) {
        return value == null ? null : Money.of(MoneyHelper.IDR_CURRENCY, Double.valueOf(value));
    }
}
