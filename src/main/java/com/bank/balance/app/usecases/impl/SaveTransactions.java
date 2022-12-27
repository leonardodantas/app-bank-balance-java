package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.usecases.ISaveTransactions;
import com.bank.balance.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveTransactions implements ISaveTransactions {

    @Override
    public void execute(final List<Transaction> transactions) {

    }
}
