package com.bank.balance.infra.database.mongo.jpa;

import com.bank.balance.infra.database.mongo.documents.BalanceEntryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceEntryMongoRepository extends MongoRepository<BalanceEntryDocument, String> {
}
