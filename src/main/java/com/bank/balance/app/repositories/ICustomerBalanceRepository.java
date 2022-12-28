package com.bank.balance.app.repositories;

import com.bank.balance.domain.CustomerBalance;

import java.util.Optional;

public interface ICustomerBalanceRepository {
    Optional<CustomerBalance> findById(final String customerId);

    CustomerBalance save(final CustomerBalance customerBalance);
}
