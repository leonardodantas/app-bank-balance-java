package com.bank.balance.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsersBalancesEntries {

    private final List<UserBalanceEntry> userBalanceEntries;

    private UsersBalancesEntries(final List<UserBalanceEntry> userBalanceEntries) {
        this.userBalanceEntries = userBalanceEntries;
    }

    public static UsersBalancesEntries from(final List<UserBalanceEntry> userBalanceEntries) {
        return new UsersBalancesEntries(userBalanceEntries);
    }

    public List<BalanceEntry> getBalanceEntries() {
        final var balanceEntries = new ArrayList<BalanceEntry>();
        userBalanceEntries.forEach(userBalanceEntry -> {
            balanceEntries.addAll(userBalanceEntry.getBalanceEntries());
        });
        return balanceEntries;
    }

    public List<String> getTransactionsIdsWithoutRepetitions(){
        return this.getBalanceEntries()
                .stream()
                .map(BalanceEntry::getTransactionId)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getTransactionsIds(){
        return this.getBalanceEntries()
                .stream()
                .map(BalanceEntry::getTransactionId)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Transaction> getTransactions(){
        return this.getBalanceEntries()
                .stream()
                .map(Transaction::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getCustomersIds(){
        return userBalanceEntries
                .stream()
                .map(UserBalanceEntry::getCustomerId)
                .collect(Collectors.toUnmodifiableList());
    }

}
