package com.bank.balance.infra.database.postgres.jpa.userBalanceEntry;

import com.bank.balance.infra.database.postgres.entities.UserBalanceEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceEntryJpaRepository extends JpaRepository<UserBalanceEntryEntity, String> {

}
