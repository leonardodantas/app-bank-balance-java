package com.bank.balance.infra.database.postgres.converters;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.TransactionType;
import com.bank.balance.infra.database.postgres.entities.BalanceEntryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BalanceEntryEntityToBalanceEntry implements Converter<BalanceEntryEntity, BalanceEntry> {

    @Override
    public BalanceEntry convert(final BalanceEntryEntity entity) {
        return new BalanceEntry(entity.getTransactionId(), entity.getDescription(), entity.getValue(),
                entity.getDate(), TransactionType.valueOf(entity.getTransactionType().name()));
    }
}
