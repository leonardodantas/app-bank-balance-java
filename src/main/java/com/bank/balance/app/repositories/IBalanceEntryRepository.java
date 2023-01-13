package com.bank.balance.app.repositories;

import com.bank.balance.domain.BalanceEntry;

import java.time.LocalDate;
import java.util.List;

public interface IBalanceEntryRepository {

    List<BalanceEntry> findBy(final String customerId, final LocalDate startDate, final LocalDate endDate, final int page, final int size);
}
