package com.bank.balance.infra.database.postgres.converters;

import com.bank.balance.domain.Transaction;
import com.bank.balance.infra.database.postgres.entities.TransactionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityToTransaction implements Converter<TransactionEntity, Transaction> {

    @Override
    public Transaction convert(final TransactionEntity entity) {
        return Transaction.of(entity.getTransactionId(), entity.getDate());
    }
}
