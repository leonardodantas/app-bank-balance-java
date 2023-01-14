package com.bank.balance.app.repositories;

import com.bank.balance.domain.UserBalanceEntry;

import java.util.List;

public interface IUserBalanceEntryEntityRepository {
    UserBalanceEntry save(final UserBalanceEntry userBalanceEntry);

    List<UserBalanceEntry> save(final List<UserBalanceEntry> userBalanceEntries);
}
