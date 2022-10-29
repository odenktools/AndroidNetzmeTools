package com.odenktools.netzmeodenktools.util;

import com.odenktools.netzmeodenktools.model.qr.MerchantQrisConst;
import com.odenktools.netzmeodenktools.model.qr.ProprietaryData;
import com.odenktools.netzmeodenktools.model.qr.QRInquiryPaymentEvent;
import com.odenktools.netzmeodenktools.model.qr.QRis;
import com.odenktools.netzmeodenktools.model.qr.QRisAdditionalData;
import com.odenktools.netzmeodenktools.model.qr.QRisMerchantInfo;

import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QRISParser extends QRTLVParser {
    public static final String DEFAULT_VALUE = "00";
    public static final String OLD_SUB_TAG_99 = "9932";
    private static List<String> merchantInfoList = Arrays.asList(TAG_MERCHANT_INFO_26, TAG_MERCHANT_INFO_27, TAG_MERCHANT_INFO_28,
            TAG_MERCHANT_INFO_29, TAG_MERCHANT_INFO_30, TAG_MERCHANT_INFO_31, TAG_MERCHANT_INFO_32, TAG_MERCHANT_INFO_33,
            TAG_MERCHANT_INFO_34, TAG_MERCHANT_INFO_35, TAG_MERCHANT_INFO_36, TAG_MERCHANT_INFO_37, TAG_MERCHANT_INFO_38,
            TAG_MERCHANT_INFO_39, TAG_MERCHANT_INFO_40, TAG_MERCHANT_INFO_41, TAG_MERCHANT_INFO_42, TAG_MERCHANT_INFO_43,
            TAG_MERCHANT_INFO_44, TAG_MERCHANT_INFO_45, TAG_MERCHANT_INFO_51);
    private static List<String> purposeTransactionTCICOList = Arrays.asList(MerchantQrisConst.PURPOSE_TRANSACTION_CASH_IN,
            MerchantQrisConst.PURPOSE_TRANSACTION_CASH_OUT,
            MerchantQrisConst.PURPOSE_TRANSACTION_TRANSFER_CROSS_BORDER,
            MerchantQrisConst.PURPOSE_TRANSACTION_TRANSFER_DOMESTIC,
            MerchantQrisConst.PURPOSE_TRANSACTION_TRANSFER_INTERNAL);

    public static QRis getQRis(Money amountInput, String qrContent) throws Exception {
        QRis qRis = new QRis();
        HashMap<String, QRisMerchantInfo> merchantInfos = new HashMap<>();


        HashMap<String, String> parseQR = parseQR(qrContent);
        Money amount = amountInput != null? amountInput : getMoney(parseQR.get(TAG_TRANSACTION_AMOUNT));
        qRis.setPayloadFormatIndicator(parseQR.get(TAG_PAYLOAD_FORMAT_INDICATOR));
        qRis.setPointOfInitiation(parseQR.get(TAG_POINT_OF_INITIATION));
        qRis.setTransactionCurrency(parseQR.get(TAG_TRANSACTION_CURRENCY));
        qRis.setTransactionAmount(getMoney(parseQR.get(TAG_TRANSACTION_AMOUNT)));
        qRis.setTipConvenience(parseQR.get(TAG_TIP_CONVENIENCE));

        if (parseQR.get(TAG_TIP_CONVENIENCE) != null) {

            qRis.setConvenienceFlag(getFlagConvenience(parseQR.get(TAG_VALUE_OF_CONVENIENCE_FIXED_FEE)));

            Money amountConvenience;

            if (parseQR.get(TAG_TIP_CONVENIENCE).equals(TIP_INDICATOR_FIX)
                    && parseQR.get(TAG_VALUE_OF_CONVENIENCE_FIXED_FEE) != null) {
                Money feeAmount = getMoney(parseQR.get(TAG_VALUE_OF_CONVENIENCE_FIXED_FEE));
                qRis.setValueConvenienceFeeFixed(feeAmount);
                amountConvenience = qRis.getValueConvenienceFeeFixed();
                qRis.setFeeAmount(amountConvenience);
            } else if (parseQR.get(TAG_TIP_CONVENIENCE).equals(TIP_INDICATOR_PROSENTASE)
                    && parseQR.get(TAG_VALUE_OF_CONVENIENCE_PERCENTAGE_FEE) != null){
                double v = Double.parseDouble(parseQR.get(TAG_VALUE_OF_CONVENIENCE_PERCENTAGE_FEE));
                qRis.setValueConvenienceFeePercentage(v);
                if (amount != null) {
                    Double tipValue = ( amount.getAmount().doubleValue() / 100) * qRis.getValueConvenienceFeePercentage();
                    amountConvenience =
                            Money.of(MoneyHelper.IDR_CURRENCY, BigDecimal.valueOf(tipValue).setScale(0, RoundingMode.CEILING));
                    qRis.setFeeAmount(amountConvenience);
                }
            }
        }

        qRis.setCountryCode(parseQR.get(TAG_COUNTRY_CODE));
        qRis.setMerchantCategoryCode(parseQR.get(TAG_MERCHANT_CATEGORY));
        qRis.setMerchantName(parseQR.get(TAG_MERCHANT_NAME));
        qRis.setMerchantCity(parseQR.get(TAG_MERCHANT_CITY));
        qRis.setPostalCode(parseQR.get(TAG_POSTAL_CODE));
        qRis.setCrc(parseQR.get(TAG_CRC));

        for (String merchantInfo: merchantInfoList) {
            if (parseQR.get(merchantInfo) != null) {
                HashMap<String, String> merchantInfoDetail = parseQR(parseQR.get(merchantInfo) );
                QRisMerchantInfo merchantInfoModel = new QRisMerchantInfo();
                merchantInfoModel.setGlobalUniqueIndentifier(merchantInfoDetail.get(SUB_TAG_GLOBAL_UNIQUE_IDENTIFIER));
                merchantInfoModel.setMerchantCriteria(merchantInfoDetail.get(SUB_TAG_MERCHANT_CRITERIA));
                merchantInfoModel.setMerchantId(merchantInfoDetail.get(SUB_TAG_MERCHANT_ID));
                merchantInfoModel.setMerchantPan(merchantInfoDetail.get(SUB_TAG_MERCHANT_PAN));
                merchantInfos.put(merchantInfo, merchantInfoModel);
            }
        }
        qRis.setMerchantInfo(merchantInfos);

        QRisAdditionalData qrisAdditionalData = new QRisAdditionalData();
        if (parseQR.get(TAG_ADDITIONAL_DATA) != null) {
            HashMap<String, String> additionalData = parseQR(parseQR.get(TAG_ADDITIONAL_DATA) );
            qrisAdditionalData.setOriginalAdditionalData(parseQR.get(TAG_ADDITIONAL_DATA));
            qrisAdditionalData.setBillNumber(additionalData.get(SUB_TAG_BILL_NUMBER));
            qrisAdditionalData.setCustomerLabel(additionalData.get(SUB_TAG_CUSTOMER_LABEL));
            qrisAdditionalData.setLoyaltyNumber(additionalData.get(SUB_TAG_LOYALTY_NUMBER));
            qrisAdditionalData.setMobileNumber(additionalData.get(SUB_TAG_MOBILE_NUMBER));
            qrisAdditionalData.setReferenceLabel(additionalData.get(SUB_TAG_REFERENCE_LABEL));
            qrisAdditionalData.setPurposeOfTransaction(additionalData.get(SUB_TAG_PURPOSE_OF_TRANSACTION));
            qrisAdditionalData.setStoreLabel(additionalData.get(SUB_TAG_STORE_LABEL));
            qrisAdditionalData.setTerminalLabel(additionalData.get(SUB_TAG_TERMINAL_LABEL));
            String additionalDataSubProprietary = additionalData.get(TAG_ADDITIONAL_DATA_SUB_PROPRIETARY);
            if (additionalDataSubProprietary != null) {
                try {
                    if (!additionalDataSubProprietary.contains(OLD_SUB_TAG_99)) {
                        HashMap<String, String> proprietaryDataResult = parseQR(additionalData.get(TAG_ADDITIONAL_DATA_SUB_PROPRIETARY));
                        if (!proprietaryDataResult.isEmpty()) {

                            ProprietaryData proprietaryData = new ProprietaryData();
                            String globallyUniqueIdentifier = proprietaryDataResult.get(TAG_ADDITIONAL_DATA_SUB_PROPRIETARY_SUB_00);
                            proprietaryData.setGloballyUniqueIdentifier((globallyUniqueIdentifier == null) ? DEFAULT_VALUE : globallyUniqueIdentifier);
                            proprietaryData.setProprietary(proprietaryDataResult.get(TAG_ADDITIONAL_DATA_SUB_PROPRIETARY_SUB_01));
                            qrisAdditionalData.setProprietaryDatas(proprietaryData);
                            qrisAdditionalData.setProprietaryData(proprietaryData.getProprietary());
                        }
                    } else {
                        qrisAdditionalData.setProprietaryData(additionalDataSubProprietary);
                    }
                } catch (Exception e) {
                    //backward compatibility
                    qrisAdditionalData.setProprietaryData(additionalDataSubProprietary);
                }
            }
        }
        qRis.setAdditionalData(qrisAdditionalData);

        return qRis;
    }

    private static String getFlagConvenience(String valueConvenience) {
        return CREDIT;
    }

    public static String getQrType(String pointOfInitiation, Money amount, QRisAdditionalData qrisAdditionalData) {
        if (qrisAdditionalData != null
                && qrisAdditionalData.getPurposeOfTransaction() != null
                && isQrisTransfer(qrisAdditionalData.getPurposeOfTransaction())) {
            if (pointOfInitiation.equals(QRInquiryPaymentEvent.QR_QRIS_DYNAMIC_FLAG)) {
                return QRInquiryPaymentEvent.QR_TYPE_QRIS_TRANSFER_DYNAMIC;
            } else {
                if (amount != null) {
                    return QRInquiryPaymentEvent.QR_TYPE_QRIS_TRANSFER_STATIC_AMOUNT;
                }
                return QRInquiryPaymentEvent.QR_TYPE_QRIS_TRANSFER_STATIC;
            }
        }
        if (pointOfInitiation.equals(QRInquiryPaymentEvent.QR_QRIS_DYNAMIC_FLAG)) {
            return QRInquiryPaymentEvent.QR_TYPE_QRIS_DYNAMIC;
        } else {
            if (amount != null) {
                return QRInquiryPaymentEvent.QR_TYPE_QRIS_STATIC_AMOUNT;
            }
            return QRInquiryPaymentEvent.QR_TYPE_QRIS_STATIC;
        }
    }

    public static boolean isQrisTransfer(String purposeOfTransaction) {
        return purposeTransactionTCICOList.contains(purposeOfTransaction);
    }

    public static Money getTotalAmount(Money amountRequested, Money feeRequested, QRis qRis) {
        Money totalAmount = MoneyHelper.IDR(0);
        if (amountRequested != null) {
            totalAmount = amountRequested;
        }
        if (qRis.getTipConvenience() != null) {
            if (qRis.getTipConvenience().equals(QRTLVParser.TIP_INDICATOR_OPEN)
                    && feeRequested != null
                    && feeRequested.isPositive()) {
                totalAmount = totalAmount.plus(feeRequested);
            } else if (qRis.getTipConvenience().equals(QRTLVParser.TIP_INDICATOR_FIX)
                    && feeRequested != null
                    && feeRequested.isPositive()) {
                totalAmount = totalAmount.plus(feeRequested);
            } else if (qRis.getConvenienceFlag().equals(QRTLVParser.CREDIT)) {
                if(qRis.getFeeAmount() != null){
                    totalAmount = totalAmount.plus(qRis.getFeeAmount());
                }
            } else if (qRis.getConvenienceFlag().equals(QRTLVParser.DEBET)) {
                totalAmount = totalAmount.minus(qRis.getDiscount());
            }
        }
        return totalAmount;
    }
}
