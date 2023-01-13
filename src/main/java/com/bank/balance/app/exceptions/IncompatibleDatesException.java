package com.bank.balance.app.exceptions;

import java.time.LocalDate;

public class IncompatibleDatesException extends RuntimeException {
    public IncompatibleDatesException(final LocalDate startDate, final LocalDate endDate) {
        super(String.format("Initial date %s should be less than or equal to end date %s", startDate, endDate));
    }
}
