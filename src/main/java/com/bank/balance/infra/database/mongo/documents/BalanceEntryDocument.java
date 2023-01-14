package com.bank.balance.infra.database.mongo.documents;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.infra.database.postgres.entities.TransactionTypeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document("balanceEntry")
public class BalanceEntryDocument {

    @Id
    private String transactionId;
    private String description;
    private BigDecimal value;
    private LocalDateTime date;
    private TransactionTypeEntity transactionType;
    private String customerId;

    private BalanceEntryDocument(final String customerId, final BalanceEntry balanceEntry) {
        this.customerId = customerId;
        this.transactionId = balanceEntry.getTransactionId();
        this.description = balanceEntry.getDescription();
        this.value = balanceEntry.getValue();
        this.date = balanceEntry.getDate();
        this.transactionType = TransactionTypeEntity.valueOf(balanceEntry.getTransactionType().name());
    }

    public static BalanceEntryDocument from(final BalanceEntry balanceEntry) {
        return new BalanceEntryDocument(balanceEntry.getCustomerId(), balanceEntry);
    }
}
