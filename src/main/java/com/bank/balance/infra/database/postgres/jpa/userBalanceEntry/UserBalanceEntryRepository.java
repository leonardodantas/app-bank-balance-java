package com.bank.balance.infra.database.postgres.jpa.userBalanceEntry;

import com.bank.balance.app.repositories.IUserBalanceEntryRepository;
import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.infra.database.postgres.converters.UserBalanceEntryEntityToUserBalanceEntry;
import com.bank.balance.infra.database.postgres.entities.UserBalanceEntryEntity;
import com.bank.balance.infra.exceptions.SaveEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class UserBalanceEntryRepository implements IUserBalanceEntryRepository {

    private final UserBalanceEntryJpaRepository userBalanceEntryJpaRepository;
    private final UserBalanceEntryEntityToUserBalanceEntry userBalanceEntryEntityToUserBalanceEntry;

    public UserBalanceEntryRepository(final UserBalanceEntryJpaRepository userBalanceEntryJpaRepository, final UserBalanceEntryEntityToUserBalanceEntry userBalanceEntryEntityToUserBalanceEntry) {
        this.userBalanceEntryJpaRepository = userBalanceEntryJpaRepository;
        this.userBalanceEntryEntityToUserBalanceEntry = userBalanceEntryEntityToUserBalanceEntry;
    }

    @Override
    public UserBalanceEntry save(final UserBalanceEntry userBalanceEntry) {
        try {
            final var userBalanceEntryEntity = UserBalanceEntryEntity.from(userBalanceEntry);
            final var userBalanceEntitySave = userBalanceEntryJpaRepository.save(userBalanceEntryEntity);
            return userBalanceEntryEntityToUserBalanceEntry.convert(userBalanceEntitySave);
        } catch (final Exception e) {
            log.error("Error to save UserBalanceEntry: {}", e.getMessage());
            throw new SaveEntityException(e.getMessage());
        }
    }

    @Override
    public List<UserBalanceEntry> save(final List<UserBalanceEntry> userBalanceEntries) {
        try {
            final var userBalanceEntriesEntity = userBalanceEntries.stream().map(UserBalanceEntryEntity::from).collect(Collectors.toUnmodifiableList());
            final var userBalanceEntriesEntitySave = userBalanceEntryJpaRepository.saveAll(userBalanceEntriesEntity);
            return userBalanceEntriesEntitySave.stream().map(userBalanceEntryEntityToUserBalanceEntry::convert).collect(Collectors.toUnmodifiableList());
        } catch (final Exception e) {
            log.error("Error to save UserBalanceEntry List: {}", e.getMessage());
            throw new SaveEntityException(e.getMessage());
        }
    }
}
