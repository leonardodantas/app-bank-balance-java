package com.bank.balance.app.repositories;

import com.bank.balance.domain.Transaction;

import java.util.List;

public interface ITransactionEntityRepository {

    List<Transaction> findByTransactionsId(final List<String> transactionsId);

    void saveAll(final List<Transaction> transactions);
}
