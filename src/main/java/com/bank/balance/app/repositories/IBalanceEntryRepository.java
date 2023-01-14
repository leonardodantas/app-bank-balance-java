package com.bank.balance.app.repositories;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.CustomerRelease;

import java.util.List;

public interface IBalanceEntryRepository {

    List<BalanceEntry> findBy(final CustomerRelease customerRelease, final int page, final int size);

    List<BalanceEntry> findAllForArchives();

    void deleteAll(final List<BalanceEntry> balanceEntries);
}
