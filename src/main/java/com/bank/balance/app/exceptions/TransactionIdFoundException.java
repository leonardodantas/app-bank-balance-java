package com.bank.balance.app.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class TransactionIdFoundException extends RuntimeException {

    private List<String> transactionsId;

    public TransactionIdFoundException(List<String> transactionsId) {
        super(String.format("%s transactions repeated on file", transactionsId));
        this.transactionsId = transactionsId;
    }
}
