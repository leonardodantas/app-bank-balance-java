package com.bank.balance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserBalanceEntries {

    private String customerId;
    private List<BalanceEntry> balanceEntries;

    private UserBalanceEntries(final String customerId, final List<BalanceEntry> balanceEntries) {
        this.customerId = customerId;
        this.balanceEntries = balanceEntries;
    }

    public static UserBalanceEntries of(final String customerId, final List<BalanceEntry> balanceEntries) {
        return new UserBalanceEntries(customerId, balanceEntries);
    }
}
