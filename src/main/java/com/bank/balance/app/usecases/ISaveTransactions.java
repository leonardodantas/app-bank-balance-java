package com.bank.balance.app.usecases;

import com.bank.balance.domain.Transaction;

import java.util.List;

public interface ISaveTransactions {

    void execute(final List<Transaction> transactions);
}
