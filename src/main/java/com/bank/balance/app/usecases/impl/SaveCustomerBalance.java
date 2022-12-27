package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.usecases.ISaveCustomerBalance;
import com.bank.balance.domain.CustomerBalance;
import org.springframework.stereotype.Service;

@Service
public class SaveCustomerBalance implements ISaveCustomerBalance {

    @Override
    public CustomerBalance execute(final CustomerBalance customerBalance) {
        return null;
    }
}
