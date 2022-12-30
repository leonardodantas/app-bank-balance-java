package com.bank.balance.app.usecases;

import com.bank.balance.domain.UsersBalancesEntriesAdapter;

public interface IEnterBalanceEntries {

    UsersBalancesEntriesAdapter execute(final UsersBalancesEntriesAdapter userBalanceEntries);
}
