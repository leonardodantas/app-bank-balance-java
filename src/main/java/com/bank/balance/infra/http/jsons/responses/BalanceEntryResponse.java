package com.bank.balance.infra.http.jsons.responses;

import com.bank.balance.domain.BalanceEntry;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class BalanceEntryResponse {

    private final String transactionId;
    private final String description;
    private final BigDecimal value;
    private final LocalDateTime date;
    private final String transactionType;

    private BalanceEntryResponse(final BalanceEntry balanceEntry) {
        this.transactionId = balanceEntry.getTransactionId();
        this.description = balanceEntry.getDescription();
        this.value = balanceEntry.getValue();
        this.date = balanceEntry.getDate();
        this.transactionType = balanceEntry.getTransactionType().name();
    }

    public static BalanceEntryResponse from(final BalanceEntry balanceEntry) {
        return new BalanceEntryResponse(balanceEntry);
    }
}
