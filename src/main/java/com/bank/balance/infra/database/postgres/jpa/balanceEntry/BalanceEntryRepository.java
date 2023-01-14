package com.bank.balance.infra.database.postgres.jpa.balanceEntry;

import com.bank.balance.app.repositories.IBalanceEntryRepository;
import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.CustomerRelease;
import com.bank.balance.infra.database.postgres.converters.BalanceEntryEntityToBalanceEntry;
import com.bank.balance.infra.database.postgres.entities.BalanceEntryEntity;
import com.bank.balance.infra.exceptions.SaveEntityException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BalanceEntryRepository implements IBalanceEntryRepository {

    private final BalanceEntryJpaRepository balanceEntryJpaRepository;
    private final BalanceEntryEntityToBalanceEntry balanceEntryEntityToBalanceEntry;

    public BalanceEntryRepository(final BalanceEntryJpaRepository balanceEntryJpaRepository, final BalanceEntryEntityToBalanceEntry balanceEntryEntityToBalanceEntry) {
        this.balanceEntryJpaRepository = balanceEntryJpaRepository;
        this.balanceEntryEntityToBalanceEntry = balanceEntryEntityToBalanceEntry;
    }

    @Override
    public List<BalanceEntry> findBy(final CustomerRelease customerRelease, final int page, final int size) {
        final var pageRequest = PageRequest.of(page, size);
        final var balanceEntriesEntity = balanceEntryJpaRepository.findAllByUserBalanceEntryCustomerIdAndDateLessThanEqualAndDateGreaterThanEqual(
                customerRelease.getCustomerId(),
                LocalDateTime.of(customerRelease.getEndDate(), LocalTime.MAX),
                LocalDateTime.of(customerRelease.getStartDate(), LocalTime.MIN),
                pageRequest
        );
        return balanceEntriesEntity.stream().map(balanceEntryEntityToBalanceEntry::convert).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BalanceEntry> findAllForArchives() {
        final var dateForArchive = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(90);
        final var balanceEntries = balanceEntryJpaRepository.findAllByDateLessThanEqual(dateForArchive);
        return balanceEntries.stream().map(balanceEntryEntityToBalanceEntry::convert).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteAll(final List<BalanceEntry> balanceEntries) {
        try {
            final var balanceEntriesEntity = balanceEntries
                    .stream().map(BalanceEntryEntity::from)
                    .collect(Collectors.toUnmodifiableList());

            balanceEntryJpaRepository.deleteAll(balanceEntriesEntity);
        } catch (final Exception exception) {
            throw new SaveEntityException(exception.getMessage(), exception.getCause());
        }
    }
}
