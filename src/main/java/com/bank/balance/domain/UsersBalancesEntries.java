package com.bank.balance.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class UsersBalancesEntries {

    private final List<UserBalanceEntry> userBalanceEntries;

    private UsersBalancesEntries(final List<UserBalanceEntry> userBalanceEntries) {
        this.userBalanceEntries = userBalanceEntries;
    }

    public static UsersBalancesEntries from(final List<UserBalanceEntry> userBalanceEntries) {
        return new UsersBalancesEntries(userBalanceEntries);
    }

}
