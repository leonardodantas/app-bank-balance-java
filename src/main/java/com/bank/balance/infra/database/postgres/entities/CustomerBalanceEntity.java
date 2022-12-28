package com.bank.balance.infra.database.postgres.entities;

import com.bank.balance.domain.CustomerBalance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "customerBalance")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerBalanceEntity {

    @Id
    private String customerId;
    @Column
    private BigDecimal balance;
    @Column
    private LocalDateTime lastUpdate;

    public static CustomerBalanceEntity from(final CustomerBalance customerBalance) {
        return new CustomerBalanceEntity(customerBalance.getCustomerId(), customerBalance.getBalance(), customerBalance.getLastUpdate());
    }
}
