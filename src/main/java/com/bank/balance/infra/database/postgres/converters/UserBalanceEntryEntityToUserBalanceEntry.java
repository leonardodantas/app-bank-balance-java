package com.bank.balance.infra.database.postgres.converters;

import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.infra.database.postgres.entities.UserBalanceEntryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserBalanceEntryEntityToUserBalanceEntry implements Converter<UserBalanceEntryEntity, UserBalanceEntry> {

    /**
     * Terminar de criar o conversor
     * @param entity
     * @return
     */
    @Override
    public UserBalanceEntry convert(final UserBalanceEntryEntity entity) {
        entity.getBalanceEntries();
        return null;
    }
}
