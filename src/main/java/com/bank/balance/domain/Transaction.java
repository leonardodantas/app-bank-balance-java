package com.bank.balance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction implements Serializable {

    private String transactionId;
    private LocalDateTime date;

    public static Transaction from(final BalanceEntry balanceEntry) {
        return new Transaction(balanceEntry.getTransactionId(), balanceEntry.getDate());
    }
}
