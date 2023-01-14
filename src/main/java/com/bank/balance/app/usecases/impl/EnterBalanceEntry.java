package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.repositories.ICustomerBalanceEntityRepository;
import com.bank.balance.app.repositories.ITransactionEntityRepository;
import com.bank.balance.app.repositories.IUserBalanceEntryEntityRepository;
import com.bank.balance.app.usecases.IEnterBalanceEntry;
import com.bank.balance.app.utils.RepeatTransactionsUtil;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.UserBalanceEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class EnterBalanceEntry implements IEnterBalanceEntry {

    private final ITransactionEntityRepository transactionRepository;
    private final IUserBalanceEntryEntityRepository balanceEntryRepository;
    private final ICustomerBalanceEntityRepository customerBalanceRepository;

    public EnterBalanceEntry(final ITransactionEntityRepository transactionRepository, final IUserBalanceEntryEntityRepository balanceEntryRepository, final ICustomerBalanceEntityRepository customerBalanceRepository) {
        this.transactionRepository = transactionRepository;
        this.balanceEntryRepository = balanceEntryRepository;
        this.customerBalanceRepository = customerBalanceRepository;
    }

    @Override
    public UserBalanceEntry execute(final UserBalanceEntry userBalanceEntry) {
        log.info("Initialized execute user balance entries");
        verifyRepeatTransactions(userBalanceEntry);
        verifyDatabaseTransactions(userBalanceEntry);
        updateUserBalance(userBalanceEntry);
        return balanceEntryRepository.save(userBalanceEntry);
    }

    private void updateUserBalance(final UserBalanceEntry userBalanceEntry) {
        final var balance = userBalanceEntry.getBalance();

        customerBalanceRepository.findById(userBalanceEntry.getCustomerId())
                .ifPresentOrElse((customerBalance) -> {
                    final var existingBalance = customerBalance.getBalance();
                    final var customerBalanceToUpdate = CustomerBalance.of(userBalanceEntry.getCustomerId(), existingBalance.add(balance));
                    customerBalanceRepository.save(customerBalanceToUpdate);
                }, () -> {
                    final var customerBalanceToSave = CustomerBalance.of(userBalanceEntry.getCustomerId(), balance);
                    customerBalanceRepository.save(customerBalanceToSave);
                });
    }

    private void verifyDatabaseTransactions(final UserBalanceEntry userBalanceEntry) {
        final var transactionsId = userBalanceEntry.getTransactionsId();
        final var existingTransactions = transactionRepository.findByTransactionsId(transactionsId);

        if (!existingTransactions.isEmpty()) {
            throw new ExistingTransactionsException(existingTransactions.stream().map(Transaction::getTransactionId).collect(Collectors.toUnmodifiableList()));
        }

        final var customerTransactions = userBalanceEntry.getTransactions();
        transactionRepository.saveAll(customerTransactions);
    }

    private void verifyRepeatTransactions(final UserBalanceEntry userBalanceEntry) {
        if (userBalanceEntry.isTransactionsRepeat()) {
            final var transactionsId = userBalanceEntry.getTransactionsId();
            final var repeatTransactionsId = RepeatTransactionsUtil.getRepeatTransactionsId(transactionsId);
            throw new TransactionIdFoundException(repeatTransactionsId);
        }
    }

}
