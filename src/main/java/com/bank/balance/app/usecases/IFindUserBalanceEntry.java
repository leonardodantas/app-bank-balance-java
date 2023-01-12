package com.bank.balance.app.usecases;

import com.bank.balance.domain.CustomerBalance;

public interface IFindUserBalanceEntry {

    CustomerBalance execute(final String customerId);
}
