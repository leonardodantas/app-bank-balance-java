package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.usecases.ISaveUserBalanceEntries;
import com.bank.balance.domain.UserBalanceEntries;
import org.springframework.stereotype.Service;

@Service
public class SaveUserBalanceEntries implements ISaveUserBalanceEntries {

    @Override
    public UserBalanceEntries execute(final UserBalanceEntries userBalanceEntries) {
        return null;
    }
}
