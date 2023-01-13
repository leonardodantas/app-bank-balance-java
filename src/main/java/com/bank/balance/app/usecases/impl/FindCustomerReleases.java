package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.DaysSearchException;
import com.bank.balance.app.exceptions.IncompatibleDatesException;
import com.bank.balance.app.repositories.IBalanceEntryRepository;
import com.bank.balance.app.usecases.IFindCustomerReleases;
import com.bank.balance.domain.BalanceEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FindCustomerReleases implements IFindCustomerReleases {

    private final IBalanceEntryRepository balanceEntryRepository;

    public FindCustomerReleases(final IBalanceEntryRepository balanceEntryRepository) {
        this.balanceEntryRepository = balanceEntryRepository;
    }

    @Override
    public List<BalanceEntry> execute(final String customerId, final LocalDate startDate, final LocalDate endDate, final int page, final int size) {
        validateDate(startDate, endDate);

        return balanceEntryRepository.findBy(
                customerId,
                startDate,
                endDate,
                page,
                size
        );
    }

    private void validateDate(final LocalDate startDate, final LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IncompatibleDatesException(startDate, endDate);
        }
        final var minDate = LocalDate.now().minusDays(90);

        if (startDate.isBefore(minDate)) {
            throw new DaysSearchException();
        }
    }
}
