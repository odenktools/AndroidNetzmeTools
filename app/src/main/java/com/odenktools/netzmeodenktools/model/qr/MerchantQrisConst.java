package com.odenktools.netzmeodenktools.model.qr;

/**
 * Created by fransisko on 2020-08-31.
 */
public class MerchantQrisConst {
    public static final String SUCCESS_TRANSFER_STATUS = "transfer_successfully";
    public static final String FAILED_TRANFER_STATUS = "transfer_failed";
    public static final String PENDING_TRANSFER_STATUS = "transfer_pending";

    public static final String PROCESSING_TRANSFER = "processing_transfer";
    public static final String PROCESSING_SETTLEMENT = "processing_settlement";
    public static final String SETTLED = "settled";
    public static final String READY_TO_SETTLED = "ready_to_settled";
    public static final String UNSETTLED = "unsettled";
    public static final String SETTLED_FAILED = "settled_failed";
    public static final String SETTLED_PENDING = "settled_pending";

    public static final String ON_US_TRANSACTION = "on_us_transaction";
    public static final String OFF_US_TRANSACTION = "off_us_transaction";

    public static final String QRIS_MERCHANT_STATIS = "qris_static";
    public static final String QRIS_MERCHANT_STATIS_TERMINAL = "qris_static_terminal";
    public static final String QRIS_MERCHANT_TERMINAL = "qris_terminal";

    public static final String TYPE_QRIS_PAYMENT = "qris_payment";
    public static final String TYPE_QRIS_TRANSFER = "qris_transfer";
    public static final String TYPE_QRIS_CASH_IN = "qris_cash_in";
    public static final String TYPE_QRIS_CASH_OUT = "qris_cash_out";

    public static final String STATE_TCICO_INQUIRY = "inquiry";
    public static final String STATE_TCICO_TRANSFER = "transfer";

    public static final String PURPOSE_TRANSACTION_TRANSFER_INTERNAL = "BOOK";
    public static final String PURPOSE_TRANSACTION_TRANSFER_DOMESTIC = "DMCT";
    public static final String PURPOSE_TRANSACTION_TRANSFER_CROSS_BORDER = "XBCT";
    public static final String PURPOSE_TRANSACTION_CASH_IN = "CDPT";
    public static final String PURPOSE_TRANSACTION_CASH_OUT = "CWDL";
}
