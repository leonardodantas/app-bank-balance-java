package com.bank.balance.infra.database.postgres.jpa;

import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class TransactionRepository implements ITransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepository(final TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public List<Transaction> findByTransactionId(final List<String> transactionId) {
        log.info("Search findByTransactionId {}", transactionId);
        return transactionJpaRepository.findAllById(transactionId);
    }
}
