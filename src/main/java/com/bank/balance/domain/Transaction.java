package com.bank.balance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction implements Serializable {

    private String transactionId;
    private LocalDateTime date;

    public static Transaction from(final BalanceEntry balanceEntry) {
        return new Transaction(balanceEntry.getTransactionId(), balanceEntry.getDate());
    }

    public static Transaction of(final String transactionId, final LocalDateTime date) {
        return new Transaction(transactionId, date);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '}';
    }
}
