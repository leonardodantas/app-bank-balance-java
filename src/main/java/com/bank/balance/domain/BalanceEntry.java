package com.bank.balance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BalanceEntry {

    private String transactionId;
    private String description;
    private BigDecimal value;
    private LocalDateTime date;
    private TransactionType TransactionType;

}
