package com.bank.balance.infra.database.postgres.entities;

import com.bank.balance.domain.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    private String transactionId;
    @Column
    private LocalDateTime date;

    private TransactionEntity(final Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.date = transaction.getDate();
    }

    public static TransactionEntity from(final Transaction transaction) {
        return new TransactionEntity(transaction);
    }

}
