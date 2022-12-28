package com.bank.balance.infra.database.postgres.jpa.transaction;

import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.domain.Transaction;
import com.bank.balance.infra.database.postgres.entities.TransactionEntity;
import com.bank.balance.infra.exceptions.SaveEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TransactionRepository implements ITransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepository(final TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    @Cacheable("transactions")
    public List<Transaction> findByTransactionsId(final List<String> transactionsId) {
        log.info("Search findByTransactionId {}", transactionsId);
        transactionJpaRepository.findAllById(transactionsId);
        return null;
    }

    @Override
    @CacheEvict("transactions")
    public void saveAll(final List<Transaction> transactions) {
        try {
            final var transactionToSave = transactions.stream()
                    .map(TransactionEntity::from)
                    .collect(Collectors.toUnmodifiableList());

            transactionJpaRepository.saveAll(transactionToSave);
        } catch (final Exception e) {
            log.error("Error save TransactionEntity: {}", e.getMessage());
            throw new SaveEntityException(e.getMessage());
        }

    }
}
