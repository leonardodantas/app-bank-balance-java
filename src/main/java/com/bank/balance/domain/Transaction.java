package com.bank.balance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction {

    private String transactionId;
    private LocalDateTime date;

    public static Transaction from(final BalanceEntry balanceEntry) {
        return new Transaction(balanceEntry.getTransactionId(), balanceEntry.getDate());
    }
}
