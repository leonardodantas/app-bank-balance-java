package com.bank.balance.infra.exceptions;

public class ConvertFileException extends RuntimeException {

    public ConvertFileException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
