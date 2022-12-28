package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.app.usecases.*;
import com.bank.balance.app.utils.RepeatTransactionsUtil;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.UserBalanceEntries;
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

    public EnterBalanceEntries(final ITransactionRepository transactionRepository, final IFindCustomerBalance findCustomerBalance, final ISaveCustomerBalance saveCustomerBalance, final ISaveTransactions saveTransactions, final ISaveUserBalanceEntries saveUserBalanceEntries) {
        this.transactionRepository = transactionRepository;
        this.findCustomerBalance = findCustomerBalance;
        this.saveCustomerBalance = saveCustomerBalance;
        this.saveTransactions = saveTransactions;
        this.saveUserBalanceEntries = saveUserBalanceEntries;
    }

    @Override
    public UserBalanceEntries execute(final UserBalanceEntries userBalanceEntries) {
        log.info("Initialized execute user balance entries");
        checkIfAnyNewTransactionsAlreadyExistInTheDatabaseAndSaveTheNewTransactions(userBalanceEntries);
        updateUserBalance(userBalanceEntries);
        return saveUserBalanceEntries.execute(userBalanceEntries);
    }

    private void updateUserBalance(final UserBalanceEntries userBalanceEntries) {
        final var balance = userBalanceEntries.getBalance();

        findCustomerBalance.execute(userBalanceEntries.getCustomerId())
                .ifPresentOrElse((customerBalance) -> {
                    final var existingBalance = customerBalance.getBalance();
                    final var customerBalanceToUpdate = CustomerBalance.of(userBalanceEntries.getCustomerId(), existingBalance.add(balance));
                    saveCustomerBalance.execute(customerBalanceToUpdate);
                }, () -> {
                    final var customerBalanceToSave = CustomerBalance.of(userBalanceEntries.getCustomerId(), balance);
                    saveCustomerBalance.execute(customerBalanceToSave);
                });
    }

    private void checkIfAnyNewTransactionsAlreadyExistInTheDatabaseAndSaveTheNewTransactions(final UserBalanceEntries userBalanceEntries) {
        final var transactionsId = checkingIfThereAreRepeatedTransactionsInTheList(userBalanceEntries);

        final var existingTransactions = transactionRepository.findByTransactionsId(transactionsId);

        if (!existingTransactions.isEmpty()) {
            throw new ExistingTransactionsException(existingTransactions.stream().map(Transaction::getTransactionId).collect(Collectors.toUnmodifiableList()));
        }

        final var customerTransactions = userBalanceEntries.getBalanceEntries().stream().map(Transaction::from).collect(Collectors.toUnmodifiableList());
        saveTransactions.execute(customerTransactions);
    }

    private List<String> checkingIfThereAreRepeatedTransactionsInTheList(final UserBalanceEntries userBalanceEntries) {
        final var transactionsId = userBalanceEntries.getTransactionsId();

        if (userBalanceEntries.isTransactionsRepeat()) {
            final var repeatTransactionsId = RepeatTransactionsUtil.getRepeatTransactionsId(transactionsId);
            throw new TransactionIdFoundException(repeatTransactionsId);
        }

        return transactionsId;
    }

    /**
     * VERIFICAR SE EXISTEM TRANSAÇÕES REPETIDAS
     * VERIFICAR SE AS TRANSAÇÕES SÃO NOVAS (NÃO PERMITIR TRANSAÇÕES REPETIDAS)
     * ATUALIZAR SALDO DO USUARIO
     *
     * @param userBalanceEntries
     * @return
     */
    @Override
    public List<UserBalanceEntries> execute(final List<UserBalanceEntries> userBalanceEntries) {
        log.info("Initialized execute list of user balance entries");
        return null;
    }
}
