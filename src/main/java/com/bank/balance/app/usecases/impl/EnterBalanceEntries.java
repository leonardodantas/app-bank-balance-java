package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.app.repositories.IUserBalanceEntryRepository;
import com.bank.balance.app.usecases.*;
import com.bank.balance.app.utils.RepeatTransactionsUtil;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.UserBalanceEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnterBalanceEntries implements IEnterBalanceEntries {

    private final ITransactionRepository transactionRepository;
    private final IFindCustomerBalance findCustomerBalance;
    private final ISaveCustomerBalance saveCustomerBalance;
    private final ISaveTransactions saveTransactions;
    private final ISaveUserBalanceEntries saveUserBalanceEntries;
    private final IUserBalanceEntryRepository balanceEntryRepository;

    public EnterBalanceEntries(final ITransactionRepository transactionRepository, final IFindCustomerBalance findCustomerBalance, final ISaveCustomerBalance saveCustomerBalance, final ISaveTransactions saveTransactions, final ISaveUserBalanceEntries saveUserBalanceEntries, final IUserBalanceEntryRepository balanceEntryRepository) {
        this.transactionRepository = transactionRepository;
        this.findCustomerBalance = findCustomerBalance;
        this.saveCustomerBalance = saveCustomerBalance;
        this.saveTransactions = saveTransactions;
        this.saveUserBalanceEntries = saveUserBalanceEntries;
        this.balanceEntryRepository = balanceEntryRepository;
    }

    @Override
    public UserBalanceEntry execute(final UserBalanceEntry userBalanceEntry) {
        log.info("Initialized execute user balance entries");
        checkIfAnyNewTransactionsAlreadyExistInTheDatabaseAndSaveTheNewTransactions(userBalanceEntry);
        updateUserBalance(userBalanceEntry);
        return balanceEntryRepository.save(userBalanceEntry);
    }

    private void updateUserBalance(final UserBalanceEntry userBalanceEntry) {
        final var balance = userBalanceEntry.getBalance();

        findCustomerBalance.execute(userBalanceEntry.getCustomerId())
                .ifPresentOrElse((customerBalance) -> {
                    final var existingBalance = customerBalance.getBalance();
                    final var customerBalanceToUpdate = CustomerBalance.of(userBalanceEntry.getCustomerId(), existingBalance.add(balance));
                    saveCustomerBalance.execute(customerBalanceToUpdate);
                }, () -> {
                    final var customerBalanceToSave = CustomerBalance.of(userBalanceEntry.getCustomerId(), balance);
                    saveCustomerBalance.execute(customerBalanceToSave);
                });
    }

    private void checkIfAnyNewTransactionsAlreadyExistInTheDatabaseAndSaveTheNewTransactions(final UserBalanceEntry userBalanceEntry) {
        final var transactionsId = checkingIfThereAreRepeatedTransactionsInTheList(userBalanceEntry);

        final var existingTransactions = transactionRepository.findByTransactionsId(transactionsId);

        if (!existingTransactions.isEmpty()) {
            throw new ExistingTransactionsException(existingTransactions.stream().map(Transaction::getTransactionId).collect(Collectors.toUnmodifiableList()));
        }

        final var customerTransactions = userBalanceEntry.getTransactions();
        saveTransactions.execute(customerTransactions);
    }

    private List<String> checkingIfThereAreRepeatedTransactionsInTheList(final UserBalanceEntry userBalanceEntry) {
        final var transactionsId = userBalanceEntry.getTransactionsId();

        if (userBalanceEntry.isTransactionsRepeat()) {
            final var repeatTransactionsId = RepeatTransactionsUtil.getRepeatTransactionsId(transactionsId);
            throw new TransactionIdFoundException(repeatTransactionsId);
        }

        return transactionsId;
    }

    @Override
    public List<UserBalanceEntry> execute(final List<UserBalanceEntry> userBalanceEntries) {
        log.info("Initialized execute list of user balance entries");

        userBalanceEntries.forEach(this::checkIfAnyNewTransactionsAlreadyExistInTheDatabaseAndSaveTheNewTransactions);
        userBalanceEntries.forEach(this::updateUserBalance);
        return userBalanceEntries
                .stream()
                .map(saveUserBalanceEntries::execute)
                .collect(Collectors.toUnmodifiableList());
    }
}
