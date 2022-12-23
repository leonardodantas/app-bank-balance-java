package com.bank.balance.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class BalanceEntry {

    private String transactionId;
    private String description;
    private BigDecimal value;
    private LocalDateTime date;

}
