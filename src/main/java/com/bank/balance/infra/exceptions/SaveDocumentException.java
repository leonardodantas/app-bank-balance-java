package com.bank.balance.infra.exceptions;

public class SaveDocumentException extends RuntimeException {

    public SaveDocumentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
