package com.bank.balance.infra.database.postgres.jpa;

import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class TransactionRepository implements ITransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepository(final TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    @Cacheable("transactions")
    public List<Transaction> findByTransactionId(final List<String> transactionId) {
        log.info("Search findByTransactionId {}", transactionId);
        return transactionJpaRepository.findAllById(transactionId);
    }
}
