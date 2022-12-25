package com.bank.balance.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Transaction {

    private String transactionId;
    private LocalDateTime date;
}
