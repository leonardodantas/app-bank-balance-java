package com.bank.balance.app.repositories;

import com.bank.balance.domain.UserBalanceEntry;

public interface IUserBalanceEntryRepository {
    UserBalanceEntry save(final UserBalanceEntry userBalanceEntry);
}
