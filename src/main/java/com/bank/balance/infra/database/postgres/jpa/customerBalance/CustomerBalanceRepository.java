package com.bank.balance.infra.database.postgres.jpa.customerBalance;

import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.domain.CustomerBalance;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerBalanceRepository implements ICustomerBalanceRepository {

    @Override
    public Optional<CustomerBalance> findById(final String customerId) {
        return Optional.empty();
    }
}
