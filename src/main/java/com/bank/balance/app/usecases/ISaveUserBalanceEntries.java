package com.bank.balance.app.usecases;

import com.bank.balance.domain.UserBalanceEntries;

public interface ISaveUserBalanceEntries {

    UserBalanceEntries execute(final UserBalanceEntries userBalanceEntries);
}
