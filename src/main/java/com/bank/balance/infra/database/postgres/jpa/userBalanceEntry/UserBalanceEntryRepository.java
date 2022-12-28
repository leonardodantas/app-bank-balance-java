package com.bank.balance.infra.database.postgres.jpa.userBalanceEntry;

import com.bank.balance.app.repositories.IUserBalanceEntryRepository;
import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.infra.database.postgres.entities.UserBalanceEntryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserBalanceEntryRepository implements IUserBalanceEntryRepository {

    private final UserBalanceEntryJpaRepository userBalanceEntryJpaRepository;

    public UserBalanceEntryRepository(final UserBalanceEntryJpaRepository userBalanceEntryJpaRepository) {
        this.userBalanceEntryJpaRepository = userBalanceEntryJpaRepository;
    }

    @Override
    public UserBalanceEntry save(final UserBalanceEntry userBalanceEntry) {
        final var userBalanceEntryEntity = UserBalanceEntryEntity.from(userBalanceEntry);
        final var userBalanceEntitySave = userBalanceEntryJpaRepository.save(userBalanceEntryEntity);
        return null;
    }
}
