package com.bank.balance.app.exceptions;

import java.util.List;

public class ExistingTransactionsException extends RuntimeException {

    public ExistingTransactionsException(final List<String> transactionsId) {
        super(String.format("transactions {} already exist", transactionsId));
    }
}
