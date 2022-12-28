package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.usecases.ISaveUserBalanceEntries;
import com.bank.balance.domain.UserBalanceEntry;
import org.springframework.stereotype.Service;

@Service
public class SaveUserBalanceEntries implements ISaveUserBalanceEntries {


    @Override
    public UserBalanceEntry execute(final UserBalanceEntry userBalanceEntry) {
        return null;
    }
}
