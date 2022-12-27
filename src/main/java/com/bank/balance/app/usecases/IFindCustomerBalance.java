package com.bank.balance.app.usecases;

import com.bank.balance.domain.CustomerBalance;

import java.util.Optional;

public interface IFindCustomerBalance {

    Optional<CustomerBalance> execute(final String customerId);
}
