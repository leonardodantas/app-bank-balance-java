package com.bank.balance.infra.database.postgres.entities;

import com.bank.balance.domain.BalanceEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "balanceEntry")
public class BalanceEntryEntity {

    @Id
    private String transactionId;

    @Column(length = 120)
    private String description;
    @Column
    private BigDecimal value;
    @Column
    private LocalDateTime date;
    @Column
    private TransactionTypeEntity transactionType;

    private BalanceEntryEntity(final BalanceEntry balanceEntry) {
        this.transactionId = this.getTransactionId();
        this.description = this.getDescription();
        this.value = balanceEntry.getValue();
        this.date = balanceEntry.getDate();
        this.transactionType = TransactionTypeEntity.valueOf(balanceEntry.getTransactionType().name());
    }

    public static BalanceEntryEntity from(final BalanceEntry balanceEntry) {
        return new BalanceEntryEntity(balanceEntry);
    }
}
