package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.repositories.IBalanceEntryDocumentRepository;
import com.bank.balance.app.repositories.IBalanceEntryEntityRepository;
import com.bank.balance.app.usecases.ISaveBalanceEntryHistory;
import org.springframework.stereotype.Service;

@Service
public class SaveBalanceEntryHistory implements ISaveBalanceEntryHistory {

    private final IBalanceEntryEntityRepository balanceEntryRepository;
    private final IBalanceEntryDocumentRepository balanceEntryDocumentRepository;

    public SaveBalanceEntryHistory(final IBalanceEntryEntityRepository balanceEntryRepository, final IBalanceEntryDocumentRepository balanceEntryDocumentRepository) {
        this.balanceEntryRepository = balanceEntryRepository;
        this.balanceEntryDocumentRepository = balanceEntryDocumentRepository;
    }

    @Override
    public void execute() {
        final var balanceEntries = balanceEntryRepository.findAllForArchives();
        if (!balanceEntries.isEmpty()) {
            balanceEntryDocumentRepository.save(balanceEntries);
            balanceEntryRepository.deleteAll(balanceEntries);
        }
    }
}
