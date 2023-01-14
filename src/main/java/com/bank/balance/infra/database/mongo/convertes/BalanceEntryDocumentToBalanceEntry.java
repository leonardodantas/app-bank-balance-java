package com.bank.balance.infra.database.mongo.convertes;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.TransactionType;
import com.bank.balance.infra.database.mongo.documents.BalanceEntryDocument;
import com.bank.balance.infra.database.postgres.entities.BalanceEntryEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BalanceEntryDocumentToBalanceEntry implements Converter<BalanceEntryDocument, BalanceEntry> {

    @Override
    public BalanceEntry convert(final BalanceEntryDocument document) {
        return new BalanceEntry(document.getTransactionId(), document.getDescription(), document.getValue(),
                document.getDate(), TransactionType.valueOf(document.getTransactionType().name()), document.getCustomerId());
    }
}
