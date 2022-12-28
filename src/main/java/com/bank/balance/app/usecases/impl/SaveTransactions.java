package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.app.usecases.ISaveTransactions;
import com.bank.balance.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveTransactions implements ISaveTransactions {

    private final ITransactionRepository transactionRepository;

    public SaveTransactions(final ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void execute(final List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }
}
