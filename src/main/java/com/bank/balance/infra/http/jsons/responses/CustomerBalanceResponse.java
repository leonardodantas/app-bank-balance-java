package com.bank.balance.infra.http.jsons.responses;

import com.bank.balance.domain.CustomerBalance;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CustomerBalanceResponse {

    private final String customerId;
    private final BigDecimal balance;
    private final LocalDateTime lastUpdate;
    private final LocalDateTime date;

    private CustomerBalanceResponse(final CustomerBalance customerBalance) {
        this.customerId = customerBalance.getCustomerId();
        this.balance = customerBalance.getBalance();
        this.lastUpdate = customerBalance.getLastUpdate();
        this.date = LocalDateTime.now();
    }

    public static CustomerBalanceResponse from(final CustomerBalance customerBalance) {
        return new CustomerBalanceResponse(customerBalance);
    }
}
