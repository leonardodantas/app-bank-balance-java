package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.exceptions.TransactionTypeInvalidException;
import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.app.usecases.IEnterBalanceEntries;
import com.bank.balance.app.utils.RepeatTransactionsUtil;
import com.bank.balance.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        verifyTransactionsBalance(userBalanceEntries);
        verifyRepeatTransactions(userBalanceEntries);
        verifyDatabaseTransactions(userBalanceEntries);
        updateUsersBalances(userBalanceEntries);
        return userBalanceEntries;
    }

    private void verifyTransactionsBalance(final UsersBalancesEntriesAdapter userBalanceEntries) {
        final var allInvalidTransactions = new ArrayList<String>();
        for (var balanceEntry : userBalanceEntries.getUserBalanceEntries()){
            final var invalidTransactions = balanceEntry.getInvalidTransactions();
            allInvalidTransactions.addAll(invalidTransactions);
        }

        throw new TransactionTypeInvalidException(allInvalidTransactions);
    }

    private void updateUsersBalances(final UsersBalancesEntriesAdapter userBalanceEntries) {
        final var customersIds = userBalanceEntries.getCustomersIds();
        final var customersBalance = customerBalanceRepository.findAllById(customersIds);
        final var customerIdNonRepeated = getCustomerIdUserBalanceEntry(userBalanceEntries, customersBalance);
        final var customersBalancesToSave = getCustomerBalancesToSave(userBalanceEntries, customersBalance, customerIdNonRepeated);
        customerBalanceRepository.save(customersBalancesToSave);
    }

    private List<CustomerBalance> getCustomerBalancesToSave(final UsersBalancesEntriesAdapter userBalanceEntries, final List<CustomerBalance> customersBalance, final Set<String> customerIdNonRepeated) {
        final var customersBalancesToSave = new ArrayList<CustomerBalance>();

        customerIdNonRepeated.forEach(customerId -> {
            final var userBalanceEntriesWithCustomerId = userBalanceEntries.getUserBalanceEntries()
                    .stream()
                    .filter(userBalanceEntry -> userBalanceEntry.getCustomerId().equalsIgnoreCase(customerId))
                    .collect(Collectors.toList());

            final var newBalance = userBalanceEntriesWithCustomerId
                    .stream()
                    .map(UserBalanceEntry::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            final var customerBalanceToSave = customersBalance
                    .stream()
                    .filter(customerBalance -> customerBalance.getCustomerId().equalsIgnoreCase(customerId))
                    .findFirst()
                    .map(customerBalanceExist -> CustomerBalance.of(customerId, customerBalanceExist.getBalance().add(newBalance)))
                    .orElse(CustomerBalance.of(customerId, newBalance));

            customersBalancesToSave.add(customerBalanceToSave);

        });
        return customersBalancesToSave;
    }

    private Set<String> getCustomerIdUserBalanceEntry(final UsersBalancesEntriesAdapter userBalanceEntries, final List<CustomerBalance> customersBalance) {
        final var customersIdsNonRepeated = userBalanceEntries.getUserBalanceEntries().stream().map(UserBalanceEntry::getCustomerId).collect(Collectors.toSet());
        final var customerBalanceNonRepeated = customersBalance.stream().map(CustomerBalance::getCustomerId).collect(Collectors.toSet());
        customersIdsNonRepeated.addAll(customerBalanceNonRepeated);
        return customersIdsNonRepeated;
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
