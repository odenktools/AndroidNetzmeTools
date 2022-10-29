package com.odenktools.netzmeodenktools.model.qr;

/**
 * Created by nanda on 11/17/16.
 */
public class QRInquiryPaymentEvent extends PaymentEvent<QRInquiryEventDetail> {

    public static final String QR_TYPE_DIMO = "qr_type_dimo";
    public static final String QR_TYPE_SCAN_TO_PAY = "scan_to_p2p";
    public static final String QR_TYPE_SCAN_SKU = "scan_sku";
    public static final String QR_TYPE_SCAN_ORDER = "scan_order";
    public static final String QR_TYPE_YAP_DYNAMIC = "qr_type_yap_dynamic";
    public static final String QR_TYPE_YAP_STATIC = "qr_type_yap_static";
    public static final String QR_TYPE_QRIS_DYNAMIC = "qr_type_qris_dynamic";
    public static final String QR_TYPE_QRIS_STATIC = "qr_type_qris_static";
    public static final String QR_TYPE_QRIS_STATIC_AMOUNT = "qr_type_qris_static_amount";
    public static final String QR_TYPE_QRIS_TRANSFER_DYNAMIC = "qr_type_qris_transfer_dynamic";
    public static final String QR_TYPE_QRIS_TRANSFER_STATIC = "qr_type_qris_transfer_static";
    public static final String QR_TYPE_QRIS_TRANSFER_STATIC_AMOUNT = "qr_type_qris_transfer_static_amount";
    public static final String QR_TYPE_COUPON = "qr_type_coupon";
    public static final String MERCHANT_INVOICE = "merchant_invoice";

    public static final String QR_YAP_STATIC_FLAG = "11";
    public static final String QR_YAP_DYNAMIC_FLAG = "12";
    public static final String QR_QRIS_STATIC_FLAG = "11";
    public static final String QR_QRIS_DYNAMIC_FLAG = "12";

    public static final String STAT_REQUEST_INFO = "request_info";
    public static final String STAT_AVAILABLE_FOR_PAYMENT = "available_for_payment";
    public static final String STAT_EXPIRED = "expired";
    public static final String STAT_PAID = "paid";
    public static final String STAT_FAILED = "failed";
    public static final String STAT_INVALID_QR_CODE = "invalid_qr_code";
    public static final String STAT_UNSUPPORTED_QR_CODE = "unsupported_qr_code";
    public static final String STAT_CLAIMED = "claimed";
    public static final String STAT_USED = "used";
    public static final String STAT_UNSUPPORTED_CURRENCY = "unsupported_currency";
    public static final String STAT_INVALID_CUSTOMER_NAME = "invalid_customer_name";
    public static final String STAT_SENDER_SAME_RECEIVER = "sender_same_receiver";

    @Override
    public UserBalanceEffect getUserBalanceEffect() {
        return UserBalanceEffect.NONE;
    }
}
