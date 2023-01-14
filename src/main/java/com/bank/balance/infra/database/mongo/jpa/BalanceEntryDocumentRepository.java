package com.bank.balance.infra.database.mongo.jpa;

import com.bank.balance.app.repositories.IBalanceEntryDocumentRepository;
import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.CustomerRelease;
import com.bank.balance.infra.database.mongo.convertes.BalanceEntryDocumentToBalanceEntry;
import com.bank.balance.infra.database.mongo.documents.BalanceEntryDocument;
import com.bank.balance.infra.exceptions.SaveDocumentException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BalanceEntryDocumentRepository implements IBalanceEntryDocumentRepository {

    private final BalanceEntryMongoRepository balanceEntryMongoRepository;
    private final BalanceEntryDocumentToBalanceEntry balanceEntryDocumentToBalanceEntry;

    public BalanceEntryDocumentRepository(final BalanceEntryMongoRepository balanceEntryMongoRepository, final BalanceEntryDocumentToBalanceEntry balanceEntryDocumentToBalanceEntry) {
        this.balanceEntryMongoRepository = balanceEntryMongoRepository;
        this.balanceEntryDocumentToBalanceEntry = balanceEntryDocumentToBalanceEntry;
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

    @Override
    public List<BalanceEntry> findBy(final CustomerRelease customerRelease, final int page, final int size) {
        final var pageRequest = PageRequest.of(page, size);
        final var balanceEntriesEntity = balanceEntryMongoRepository.findAllByUserBalanceEntryCustomerIdAndDateLessThanEqualAndDateGreaterThanEqual(
                customerRelease.getCustomerId(),
                LocalDateTime.of(customerRelease.getEndDate(), LocalTime.MAX),
                LocalDateTime.of(customerRelease.getStartDate(), LocalTime.MIN),
                pageRequest
        );
        return balanceEntriesEntity.stream().map(balanceEntryDocumentToBalanceEntry::convert).collect(Collectors.toUnmodifiableList());
    }
}
