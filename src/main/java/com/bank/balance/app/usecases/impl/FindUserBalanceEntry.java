package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.CustomerIdNotFoundException;
import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.app.usecases.IFindUserBalanceEntry;
import com.bank.balance.domain.CustomerBalance;
import org.springframework.stereotype.Service;

@Service
public class FindUserBalanceEntry implements IFindUserBalanceEntry {

    private final ICustomerBalanceRepository customerBalanceRepository;

    public FindUserBalanceEntry(final ICustomerBalanceRepository customerBalanceRepository) {
        this.customerBalanceRepository = customerBalanceRepository;
    }

    @Override
    public CustomerBalance execute(final String customerId) {
        return customerBalanceRepository.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(customerId));
    }
}
