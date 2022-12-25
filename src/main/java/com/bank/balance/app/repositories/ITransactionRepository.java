package com.bank.balance.app.repositories;

import com.bank.balance.domain.Transaction;

import java.util.List;

public interface ITransactionRepository {

    List<Transaction> findByTransactionId(final List<String> transactionId);
}
