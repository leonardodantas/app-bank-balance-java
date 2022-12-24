package com.bank.balance.app.usecases;

import com.bank.balance.domain.UserBalanceEntries;

import java.util.List;

public interface IEnterBalanceEntries {

    UserBalanceEntries execute(final UserBalanceEntries userBalanceEntries);
    List<UserBalanceEntries> execute(final List<UserBalanceEntries> userBalanceEntries);
}
