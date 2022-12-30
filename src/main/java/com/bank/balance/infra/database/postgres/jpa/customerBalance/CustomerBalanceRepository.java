package com.bank.balance.infra.database.postgres.jpa.customerBalance;

import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.infra.database.postgres.converters.CustomerBalanceEntityToCustomerBalance;
import com.bank.balance.infra.database.postgres.entities.CustomerBalanceEntity;
import com.bank.balance.infra.exceptions.SaveEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CustomerBalanceRepository implements ICustomerBalanceRepository {

    private final CustomerBalanceJpaRepository customerBalanceJpaRepository;
    private final CustomerBalanceEntityToCustomerBalance customerBalanceEntityToCustomerBalance;

    public CustomerBalanceRepository(final CustomerBalanceJpaRepository customerBalanceJpaRepository, final CustomerBalanceEntityToCustomerBalance customerBalanceEntityToCustomerBalance) {
        this.customerBalanceJpaRepository = customerBalanceJpaRepository;
        this.customerBalanceEntityToCustomerBalance = customerBalanceEntityToCustomerBalance;
    }

    @Override
    public Optional<CustomerBalance> findById(final String customerId) {
        return customerBalanceJpaRepository.findById(customerId)
                .map(customerBalanceExist -> {
                    final var customerBalance = customerBalanceEntityToCustomerBalance.convert(customerBalanceExist);
                    return Optional.ofNullable(customerBalance);
                }).orElse(Optional.empty());
    }

    @Override
    public CustomerBalance save(final CustomerBalance customerBalance) {
        try {
            final var customerEntity = CustomerBalanceEntity.from(customerBalance);
            final var customerEntitySave = customerBalanceJpaRepository.save(customerEntity);
            return customerBalanceEntityToCustomerBalance.convert(customerEntitySave);
        } catch (final Exception e) {
            log.error("Error save customerEntity: {}", e.getMessage());
            throw new SaveEntityException(e.getMessage());
        }

    }

    @Override
    public List<CustomerBalance> findAllById(final List<String> customersIds) {
        final var customerBalanceEntity = customerBalanceJpaRepository.findAllById(customersIds);
        return customerBalanceEntity.stream().map(customerBalanceEntityToCustomerBalance::convert).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void save(final List<CustomerBalance> customerBalances) {
        try {
            final var customerBalancesEntity = customerBalances
                    .stream()
                    .map(CustomerBalanceEntity::from)
                    .collect(Collectors.toUnmodifiableList());
            customerBalanceJpaRepository.saveAll(customerBalancesEntity);
        } catch (final Exception e){
            log.error("Error save customerEntity List: {}", e.getMessage());
            throw new SaveEntityException(e.getMessage());
        }
    }
}
