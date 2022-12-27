package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.usecases.IFindCustomerBalance;
import com.bank.balance.domain.CustomerBalance;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindCustomerBalance implements IFindCustomerBalance {

    @Override
    public Optional<CustomerBalance> execute(final String customerId) {
        return null;
    }
}
