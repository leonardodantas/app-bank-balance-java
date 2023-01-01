package com.bank.balance.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class UsersBalancesEntries {

    @NotNull
    private final List<UserBalanceEntry> userBalanceEntries;

    private UsersBalancesEntries(final List<UserBalanceEntry> userBalanceEntries) {
        this.userBalanceEntries = userBalanceEntries;
    }

    public static UsersBalancesEntries from(final List<UserBalanceEntry> userBalanceEntries) {
        return new UsersBalancesEntries(userBalanceEntries);
    }

}
