package com.bank.balance.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class CustomerRelease {

    private final String customerId;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public boolean isEndDateBeforeStartDate() {
        return endDate.isBefore(startDate);
    }

    public boolean isStartDateBeforeMinDate() {
        final var minDate = LocalDate.now().minusDays(90);
        return startDate.isBefore(minDate);
    }
}
