package com.bank.balance.infra.database.postgres.jpa;

import com.bank.balance.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<Transaction, String> {
}
