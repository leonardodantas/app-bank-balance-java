package com.bank.balance.app.usecases;

import com.bank.balance.domain.UserBalanceEntry;

import java.util.List;

public interface IEnterBalanceEntries {

    UserBalanceEntry execute(final UserBalanceEntry userBalanceEntry);
    List<UserBalanceEntry> execute(final List<UserBalanceEntry> userBalanceEntries);
}
