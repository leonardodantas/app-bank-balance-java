package com.bank.balance.infra.database.postgres.entities;

import com.bank.balance.domain.BalanceEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private TransactionTypeEntity transactionType;
    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserBalanceEntryEntity userBalanceEntry;

    private BalanceEntryEntity(final String customerId, final BalanceEntry balanceEntry) {
        this.transactionId = balanceEntry.getTransactionId();
        this.description = balanceEntry.getDescription();
        this.value = balanceEntry.getValue();
        this.date = balanceEntry.getDate();
        this.transactionType = TransactionTypeEntity.valueOf(balanceEntry.getTransactionType().name());
        this.userBalanceEntry = UserBalanceEntryEntity.from(customerId);
    }

    public static BalanceEntryEntity from(final String customerId, final BalanceEntry balanceEntry) {
        return new BalanceEntryEntity(customerId, balanceEntry);
    }

    public static BalanceEntryEntity from(final BalanceEntry balanceEntry) {
        return new BalanceEntryEntity(balanceEntry.getCustomerId(), balanceEntry);
    }

    public String getCustomerId() {
        return this.userBalanceEntry.getCustomerId();
    }
}
