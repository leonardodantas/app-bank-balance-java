package com.bank.balance.app.usecases;

import com.bank.balance.domain.UsersBalancesEntries;

public interface IEnterBalanceEntries {

    UsersBalancesEntries execute(final UsersBalancesEntries userBalanceEntries);
}
