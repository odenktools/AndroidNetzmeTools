package com.odenktools.netzmeodenktools.model.qr;

import org.joda.money.Money;

/**
 * Created by ahmadulil on 8/11/16.
 */
public abstract class PaymentEvent<T> {
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_DETAILS = "details";

    public static final int NO_SEQ = -1;

    private int seq = NO_SEQ;
    private long ts;
    private String type;
    private String msgId;
    private String refId;
    private String userId;
    private Money amount;
    private T details;
    private GenericError error;
    private String couponId;
    private boolean isDuplicateEvent = false;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
    
    public T getDetails() {
        return details;
    }
    public void setDetails(T details) {
        this.details = details;
    }

    public GenericError getError() {
        return error;
    }

    public void setError(GenericError error) {
        this.error = error;
    }

    public abstract UserBalanceEffect getUserBalanceEffect();

    public boolean isDuplicateEvent() {
        return isDuplicateEvent;
    }

    public void setDuplicateEvent(boolean duplicateEvent) {
        isDuplicateEvent = duplicateEvent;
    }
}
