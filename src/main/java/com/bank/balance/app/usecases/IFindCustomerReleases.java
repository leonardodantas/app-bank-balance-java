package com.bank.balance.app.usecases;

import com.bank.balance.domain.BalanceEntry;

import java.time.LocalDate;
import java.util.List;

public interface IFindCustomerReleases {

    List<BalanceEntry> execute(final String customerId, final LocalDate startDate, final LocalDate endDate, final int page, final int size);
}
