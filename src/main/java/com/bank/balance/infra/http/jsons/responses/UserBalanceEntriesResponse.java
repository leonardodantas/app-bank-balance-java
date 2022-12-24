package com.bank.balance.infra.http.jsons.responses;

import com.bank.balance.domain.UserBalanceEntries;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserBalanceEntriesResponse {

    private final String customerId;
    private final List<BalanceEntryResponse> balanceEntries;

    private UserBalanceEntriesResponse(final String customerId, final List<BalanceEntryResponse> balanceEntries) {
        this.customerId = customerId;
        this.balanceEntries = balanceEntries;
    }

    public static UserBalanceEntriesResponse from(final UserBalanceEntries userBalanceEntries) {
        final var balanceEntriesResponses = userBalanceEntries.getBalanceEntries().stream().map(BalanceEntryResponse::from).collect(Collectors.toUnmodifiableList());
        return new UserBalanceEntriesResponse(userBalanceEntries.getCustomerId(), balanceEntriesResponses);
    }
}
