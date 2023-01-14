package com.bank.balance.infra.database.postgres.jpa.balanceEntry;

import com.bank.balance.infra.database.postgres.entities.BalanceEntryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceEntryJpaRepository extends JpaRepository<BalanceEntryEntity, String> {
    Page<BalanceEntryEntity> findAllByUserBalanceEntryCustomerIdAndDateLessThanEqualAndDateGreaterThanEqual(final String customerId, final LocalDateTime endDate, final LocalDateTime startDate, final PageRequest pageRequest);

    List<BalanceEntryEntity> findAllByDateLessThanEqual(final LocalDateTime date);
}
