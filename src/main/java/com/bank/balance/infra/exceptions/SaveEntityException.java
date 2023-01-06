package com.bank.balance.infra.exceptions;

public class SaveEntityException extends RuntimeException {

    public SaveEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
