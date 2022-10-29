package com.odenktools.netzmeodenktools.util;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;

/**
 * Created by ahmadulil on 6/15/16.
 */
public class MoneyHelper {
    public static final CurrencyUnit IDR_CURRENCY = CurrencyUnit.of("IDR");
    public static final Money NOL = Money.zero(IDR_CURRENCY);

    public static Money IDR(long amount) {
        return Money.ofMajor(IDR_CURRENCY, amount);
    }

    public static Money parse(BigDecimal amount, String currency, Money defaultIfNull) {
        return amount != null ? Money.of(CurrencyUnit.of(currency), amount) : defaultIfNull;
    }
}
