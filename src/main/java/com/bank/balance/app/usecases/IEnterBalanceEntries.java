package com.bank.balance.app.usecases;

import com.bank.balance.domain.UserBalanceEntry;

import java.util.List;

public interface IEnterBalanceEntries {

    List<UserBalanceEntry> execute(final List<UserBalanceEntry> userBalanceEntries);
}
