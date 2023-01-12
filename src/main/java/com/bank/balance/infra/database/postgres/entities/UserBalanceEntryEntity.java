package com.bank.balance.infra.database.postgres.entities;

import com.bank.balance.domain.BalanceEntry;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userBalanceEntry")
    private List<BalanceEntryEntity> balanceEntries;

    private UserBalanceEntryEntity(final String customerId) {
        this.customerId = customerId;
    }

    public static UserBalanceEntryEntity of(final String customerId, final List<BalanceEntry> balanceEntries) {

        final var balanceEntriesEntity = balanceEntries
                .stream()
                .map(balanceEntryEntity -> {
                    return BalanceEntryEntity.from(customerId, balanceEntryEntity);
                })
                .collect(Collectors.toUnmodifiableList());

        return new UserBalanceEntryEntity(customerId, balanceEntriesEntity);
    }

    public static UserBalanceEntryEntity from(final String customerId) {
        return new UserBalanceEntryEntity(customerId);
    }
}
