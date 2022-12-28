package com.bank.balance.infra.database.postgres.jpa.customerBalance;

import com.bank.balance.infra.database.postgres.entities.CustomerBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerBalanceJpaRepository extends JpaRepository<CustomerBalanceEntity, String> {
}
