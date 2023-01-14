package com.bank.balance.infra.database.mongo.jpa;

import com.bank.balance.app.repositories.IBalanceEntryDocumentRepository;
import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.infra.database.mongo.documents.BalanceEntryDocument;
import com.bank.balance.infra.exceptions.SaveDocumentException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BalanceEntryDocumentRepository implements IBalanceEntryDocumentRepository {

    private final BalanceEntryMongoRepository balanceEntryMongoRepository;

    public BalanceEntryDocumentRepository(final BalanceEntryMongoRepository balanceEntryMongoRepository) {
        this.balanceEntryMongoRepository = balanceEntryMongoRepository;
    }

    @Override
    public void save(final List<BalanceEntry> balanceEntries) {
        try {
            final var balanceEntriesDocuments = balanceEntries
                    .stream()
                    .map(BalanceEntryDocument::from)
                    .collect(Collectors.toUnmodifiableList());

            balanceEntryMongoRepository.saveAll(balanceEntriesDocuments);
        } catch (final Exception exception) {
            throw new SaveDocumentException(exception.getMessage(), exception.getCause());
        }
    }
}
