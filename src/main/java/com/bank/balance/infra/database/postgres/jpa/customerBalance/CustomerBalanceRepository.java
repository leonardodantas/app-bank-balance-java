package com.bank.balance.infra.database.postgres.jpa.customerBalance;

import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.infra.database.postgres.converters.CustomerBalanceEntityToCustomerBalance;
import com.bank.balance.infra.database.postgres.entities.CustomerBalanceEntity;
import com.bank.balance.infra.exceptions.SaveEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
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
        try {
            final var customerEntity = CustomerBalanceEntity.from(customerBalance);
            final var customerEntitySave = customerBalanceRepository.save(customerEntity);
            return customerBalanceEntityToCustomerBalance.convert(customerEntitySave);
        } catch (final Exception e) {
            log.error("Error save customerEntity: {}", e.getMessage());
            throw new SaveEntityException(e.getMessage());
        }

    }
}
