package com.bank.balance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerBalance {

    private String customerId;
    private BigDecimal balance;
    private LocalDateTime lastUpdate;

    public static CustomerBalance of(final String customerId, final BigDecimal balance) {
        return new CustomerBalance(customerId, balance, LocalDateTime.now());
    }
}
