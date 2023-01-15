package com.bank.balance.infra.database.mongo.jpa;

import com.bank.balance.infra.database.mongo.documents.BalanceEntryDocument;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BalanceEntryMongoRepository extends MongoRepository<BalanceEntryDocument, String> {
    List<BalanceEntryDocument> findAllByDateBetweenAndCustomerId(final LocalDateTime startDate, final LocalDateTime endDate, final String customerId, final PageRequest pageRequest);
}
