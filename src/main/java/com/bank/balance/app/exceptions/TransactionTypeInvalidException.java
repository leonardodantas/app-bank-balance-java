package com.bank.balance.app.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class TransactionTypeInvalidException extends RuntimeException {
    private final List<String> invalidTransactions;

    public TransactionTypeInvalidException(final List<String> invalidTransactions) {
        super(String.format("Invalid transactions %s", invalidTransactions));
        this.invalidTransactions = invalidTransactions;
    }
}
