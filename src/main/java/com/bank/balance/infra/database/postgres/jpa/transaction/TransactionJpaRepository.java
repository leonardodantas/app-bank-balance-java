package com.bank.balance.infra.database.postgres.jpa.transaction;

import com.bank.balance.infra.database.postgres.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, String> {
}
