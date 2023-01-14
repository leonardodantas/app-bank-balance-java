package com.bank.balance.app.repositories;

import com.bank.balance.domain.CustomerBalance;

import java.util.List;
import java.util.Optional;

public interface ICustomerBalanceEntityRepository {
    Optional<CustomerBalance> findById(final String customerId);

    CustomerBalance save(final CustomerBalance customerBalance);

    List<CustomerBalance> findAllById(final List<String> customersIds);

    void save(final List<CustomerBalance> customerBalances);
}
