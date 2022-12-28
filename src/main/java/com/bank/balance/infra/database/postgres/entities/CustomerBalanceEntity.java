package com.bank.balance.infra.database.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customerBalance")
public class CustomerBalanceEntity {

    @Id
    private String customerId;
    @Column
    private BigDecimal balance;
    @Column
    private LocalDateTime lastUpdate;
}
