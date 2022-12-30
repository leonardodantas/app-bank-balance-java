package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.app.usecases.IEnterBalanceEntries;
import com.bank.balance.app.utils.RepeatTransactionsUtil;
import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.domain.Transaction;
import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.domain.UsersBalancesEntriesAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnterBalanceEntries implements IEnterBalanceEntries {

    private final ITransactionRepository transactionRepository;
    private final ICustomerBalanceRepository customerBalanceRepository;

    public EnterBalanceEntries(final ITransactionRepository transactionRepository, final ICustomerBalanceRepository customerBalanceRepository) {
        this.transactionRepository = transactionRepository;
        this.customerBalanceRepository = customerBalanceRepository;
    }

    @Override
    public UsersBalancesEntriesAdapter execute(final UsersBalancesEntriesAdapter userBalanceEntries) {
        verifyRepeatTransactions(userBalanceEntries);
        verifyDatabaseTransactions(userBalanceEntries);
        updateUsersBalances(userBalanceEntries);
        return userBalanceEntries;
    }

    private void updateUsersBalances(final UsersBalancesEntriesAdapter userBalanceEntries) {
        // TODO: 30/12/2022 se nunca foi salvo na primeira vez, como vai ser atualizado?
        final var customersIds = userBalanceEntries.getCustomersIds();
        final var customersBalance = customerBalanceRepository.findAllById(customersIds);

        final var customersBalanceToUpdate = new ArrayList<CustomerBalance>();

        customersBalance.forEach(customerBalance -> {
            final var allBalances = userBalanceEntries
                    .getUserBalanceEntries()
                    .stream()
                    .filter(userBalanceEntry -> userBalanceEntry.getCustomerId().equalsIgnoreCase(customerBalance.getCustomerId()))
                    .collect(Collectors.toUnmodifiableList());

            final var balance = allBalances.stream().map(UserBalanceEntry::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
            customersBalanceToUpdate.add(CustomerBalance.of(customerBalance.getCustomerId(), customerBalance.getBalance().add(balance), LocalDateTime.now()));
        });

        customerBalanceRepository.save(customersBalanceToUpdate);
    }

    private void verifyDatabaseTransactions(final UsersBalancesEntriesAdapter userBalanceEntries) {
        final var transactionsId = userBalanceEntries.getTransactionsIds();

        final var existingTransactions = transactionRepository.findByTransactionsId(transactionsId);

        if (!existingTransactions.isEmpty()) {
            throw new ExistingTransactionsException(existingTransactions.stream().map(Transaction::getTransactionId).collect(Collectors.toUnmodifiableList()));
        }

        final var customerTransactions = userBalanceEntries.getTransactions();
        transactionRepository.saveAll(customerTransactions);
    }

    private void verifyRepeatTransactions(final UsersBalancesEntriesAdapter userBalanceEntries) {
        if (userBalanceEntries.isRepeated()) {
            final var transactionsId = userBalanceEntries.getTransactionsIds();
            final var repeatTransactionsId = RepeatTransactionsUtil.getRepeatTransactionsId(transactionsId);
            throw new TransactionIdFoundException(repeatTransactionsId);
        }
    }
}
