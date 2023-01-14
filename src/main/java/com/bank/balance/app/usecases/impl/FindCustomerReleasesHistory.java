package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.DaysSearchException;
import com.bank.balance.app.exceptions.IncompatibleDatesException;
import com.bank.balance.app.repositories.IBalanceEntryDocumentRepository;
import com.bank.balance.app.repositories.IBalanceEntryEntityRepository;
import com.bank.balance.app.usecases.IFindCustomerReleasesHistory;
import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.CustomerRelease;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FindCustomerReleasesHistory implements IFindCustomerReleasesHistory {

    private final IBalanceEntryDocumentRepository balanceEntryDocumentRepository;

    public FindCustomerReleasesHistory(final IBalanceEntryDocumentRepository balanceEntryDocumentRepository) {
        this.balanceEntryDocumentRepository = balanceEntryDocumentRepository;
    }

    @Override
    public List<BalanceEntry> execute(final CustomerRelease customerRelease, final int page, final int size) {
        validateDate(customerRelease);
        return balanceEntryDocumentRepository.findBy(customerRelease, page, size);
    }

    private void validateDate(final CustomerRelease customerRelease) {
        if (customerRelease.isEndDateBeforeStartDate()) {
            throw new IncompatibleDatesException(customerRelease.getStartDate(), customerRelease.getEndDate());
        }

        if (!customerRelease.isStartDateBeforeMinDate()) {
            throw new DaysSearchException(String.format("O periodo maximo para buscar deve ser 90 dias, ou seja %s", LocalDate.now().minusDays(90)));
        }
    }
}
