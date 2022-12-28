package com.bank.balance.app.usecases;

import com.bank.balance.domain.UserBalanceEntry;

public interface ISaveUserBalanceEntries {

    UserBalanceEntry execute(final UserBalanceEntry userBalanceEntry);
}
