package com.bank.balance.app.usecases;

import com.bank.balance.domain.CustomerBalance;

public interface ISaveCustomerBalance {

    CustomerBalance execute(final CustomerBalance customerBalance);
}
