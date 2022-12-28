package com.bank.balance.infra.database.postgres.jpa.customerBalance;

import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.infra.database.postgres.converters.CustomerBalanceEntityToCustomerBalance;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerBalanceRepository implements ICustomerBalanceRepository {

    private final CustomerBalanceJpaRepository customerBalanceRepository;
    private final CustomerBalanceEntityToCustomerBalance customerBalanceEntityToCustomerBalance;

    public CustomerBalanceRepository(final CustomerBalanceJpaRepository customerBalanceRepository, final CustomerBalanceEntityToCustomerBalance customerBalanceEntityToCustomerBalance) {
        this.customerBalanceRepository = customerBalanceRepository;
        this.customerBalanceEntityToCustomerBalance = customerBalanceEntityToCustomerBalance;
    }

    @Override
    public Optional<CustomerBalance> findById(final String customerId) {
        return customerBalanceRepository.findById(customerId)
                .map(customerBalanceExist -> {
                    final var customerBalance = customerBalanceEntityToCustomerBalance.convert(customerBalanceExist);
                    return Optional.ofNullable(customerBalance);
                }).orElse(Optional.empty());
    }

    @Override
    public CustomerBalance save(final CustomerBalance customerBalance) {
        return null;
    }
}
