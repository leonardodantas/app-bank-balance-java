package com.bank.balance.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class UsersBalancesEntriesAdapter {

    private final List<UserBalanceEntry> userBalanceEntries;

    public UsersBalancesEntriesAdapter() {
        this.userBalanceEntries = List.of();
    }

    private UsersBalancesEntriesAdapter(final List<UserBalanceEntry> userBalanceEntries) {
        this.userBalanceEntries = userBalanceEntries;
    }

    public static UsersBalancesEntriesAdapter from(final List<UserBalanceEntry> userBalanceEntries) {
        return new UsersBalancesEntriesAdapter(userBalanceEntries);
    }

    public static UsersBalancesEntriesAdapter from(final UserBalanceEntry userBalanceEntries) {
        return new UsersBalancesEntriesAdapter(Collections.singletonList(userBalanceEntries));
    }

    public static UsersBalancesEntriesAdapter from(final UsersBalancesEntries usersBalanceEntries) {
        return new UsersBalancesEntriesAdapter(usersBalanceEntries.getUserBalanceEntries());
    }

    private List<BalanceEntry> getBalanceEntries() {
        final var balanceEntries = new ArrayList<BalanceEntry>();
        userBalanceEntries.forEach(userBalanceEntry -> {
            balanceEntries.addAll(userBalanceEntry.getBalanceEntries());
        });
        return Collections.unmodifiableList(balanceEntries);
    }

    private List<String> getTransactionsIdsWithoutRepetitions() {
        return this.getBalanceEntries()
                .stream()
                .map(BalanceEntry::getTransactionId)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getNonRepeatingTransactionsIds() {
        return this.getBalanceEntries()
                .stream()
                .map(BalanceEntry::getTransactionId)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getTransactionsIds() {
        return this.getBalanceEntries()
                .stream()
                .map(BalanceEntry::getTransactionId)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Transaction> getTransactions() {
        return this.getBalanceEntries()
                .stream()
                .map(Transaction::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getCustomersIds() {
        return userBalanceEntries
                .stream()
                .map(UserBalanceEntry::getCustomerId)
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean isRepeated() {
        return this.getBalanceEntries().size() != this.getTransactionsIdsWithoutRepetitions().size();
    }

    public UserBalanceEntry getOne() {
        return Optional.ofNullable(this.userBalanceEntries.get(0))
                .orElse(new UserBalanceEntry());

    }

}
