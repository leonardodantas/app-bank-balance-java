package com.bank.balance.domain;

import java.math.BigDecimal;

public enum TransactionType {
    DEPOSIT() {
        @Override
        public boolean isInvalid(final BigDecimal value) {
            return BigDecimal.ZERO.compareTo(value) > 0;
        }
    }, WITHDRAW() {
        @Override
        public boolean isInvalid(final BigDecimal value) {
            return BigDecimal.ZERO.compareTo(value) < 0;
        }
    };

    public abstract boolean isInvalid(final BigDecimal value);
}
