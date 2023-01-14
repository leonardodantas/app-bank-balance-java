package com.bank.balance.app.repositories;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.CustomerRelease;

import java.util.List;

public interface IBalanceEntryDocumentRepository {

    void save(final List<BalanceEntry> balanceEntries);

    List<BalanceEntry> findBy(final CustomerRelease customerRelease, final int page, final int size);

}
