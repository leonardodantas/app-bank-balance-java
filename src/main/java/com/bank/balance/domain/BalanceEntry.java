package com.bank.balance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceEntry {

    @NotBlank
    private String transactionId;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal value;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private TransactionType transactionType;
    private String customerId;
    public boolean isInvalidTransaction() {
        return transactionType.isInvalid(value);
    }

}
