package com.bank.balance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRelease {

    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;

    public boolean isEndDateBeforeStartDate() {
        return endDate.isBefore(startDate);
    }

    public boolean isStartDateBeforeMinDate() {
        final var minDate = LocalDate.now().minusDays(90);
        return startDate.isBefore(minDate);
    }
}
