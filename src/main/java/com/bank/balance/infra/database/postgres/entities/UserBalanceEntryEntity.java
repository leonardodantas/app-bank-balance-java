package com.bank.balance.infra.database.postgres.entities;

import com.bank.balance.domain.UserBalanceEntry;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "userBalanceEntry")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBalanceEntryEntity {

    @Id
    private String customerId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BalanceEntryEntity> balanceEntries;

    public static UserBalanceEntryEntity from(final UserBalanceEntry userBalanceEntry) {
        final var balanceEntries = userBalanceEntry
                .getBalanceEntries().stream().map(BalanceEntryEntity::from)
                .collect(Collectors.toUnmodifiableList());
        return new UserBalanceEntryEntity(userBalanceEntry.getCustomerId(), balanceEntries);
    }
}
