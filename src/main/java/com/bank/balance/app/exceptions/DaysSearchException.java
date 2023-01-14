package com.bank.balance.app.exceptions;

import java.time.LocalDate;

public class DaysSearchException extends RuntimeException {

    public DaysSearchException(final String message) {
        super(message);
    }
}
