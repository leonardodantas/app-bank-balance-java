package com.bank.balance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserBalanceEntry {

    @NotBlank
    private String customerId;

    @NotNull
    private List<BalanceEntry> balanceEntries;

    private UserBalanceEntry(final String customerId, final List<BalanceEntry> balanceEntries) {
        this.customerId = customerId;
        this.balanceEntries = balanceEntries;
    }

    public static UserBalanceEntry of(final String customerId, final List<BalanceEntry> balanceEntries) {
        return new UserBalanceEntry(customerId, balanceEntries);
    }

    public List<String> getTransactionsId() {
        return balanceEntries.stream().map(BalanceEntry::getTransactionId).collect(Collectors.toUnmodifiableList());
    }

    private List<String> getNonRepeatTransactionsId() {
        return getBalanceEntries().stream().map(BalanceEntry::getTransactionId).distinct().collect(Collectors.toList());
    }

    public boolean isTransactionsRepeat() {
        return this.getNonRepeatTransactionsId().size() != this.getTransactionsId().size();
    }

    public BigDecimal getBalance() {
        final var balanceToAdd = this.getBalanceEntries().stream()
                .filter(balanceEntry -> balanceEntry.getTransactionType().equals(TransactionType.DEPOSIT))
                .map(BalanceEntry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final var balanceForWithdrawal = this.getBalanceEntries().stream()
                .filter(balanceEntry -> balanceEntry.getTransactionType().equals(TransactionType.WITHDRAW))
                .map(BalanceEntry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return balanceToAdd.add(balanceForWithdrawal);
    }

    public List<Transaction> getTransactions() {
        return this.getBalanceEntries()
                .stream().map(Transaction::from).collect(Collectors.toUnmodifiableList());
    }

    public List<String> getInvalidTransactions(){
        return this.balanceEntries
                .stream()
                .filter(BalanceEntry::isInvalidTransaction)
                .map(BalanceEntry::getTransactionId)
                .collect(Collectors.toUnmodifiableList());
    }
}
