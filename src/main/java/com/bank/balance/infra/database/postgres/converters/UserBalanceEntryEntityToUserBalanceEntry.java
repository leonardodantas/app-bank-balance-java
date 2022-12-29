package com.bank.balance.infra.database.postgres.converters;

import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.infra.database.postgres.entities.UserBalanceEntryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserBalanceEntryEntityToUserBalanceEntry implements Converter<UserBalanceEntryEntity, UserBalanceEntry> {

    private final BalanceEntryEntityToBalanceEntry balanceEntryEntityToBalanceEntry;

    public UserBalanceEntryEntityToUserBalanceEntry(final BalanceEntryEntityToBalanceEntry balanceEntryEntityToBalanceEntry) {
        this.balanceEntryEntityToBalanceEntry = balanceEntryEntityToBalanceEntry;
    }

    @Override
    public UserBalanceEntry convert(final UserBalanceEntryEntity entity) {
        final var balanceEntries = entity.getBalanceEntries().stream().map(balanceEntryEntityToBalanceEntry::convert).collect(Collectors.toUnmodifiableList());
        return UserBalanceEntry.of(entity.getCustomerId(), balanceEntries);
    }
}
