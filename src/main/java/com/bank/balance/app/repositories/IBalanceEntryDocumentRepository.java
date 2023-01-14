package com.bank.balance.app.repositories;

import com.bank.balance.domain.BalanceEntry;

import java.util.List;

public interface IBalanceEntryDocumentRepository {

    void save(final List<BalanceEntry> balanceEntries);
}
