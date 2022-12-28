package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.app.usecases.ISaveCustomerBalance;
import com.bank.balance.domain.CustomerBalance;
import org.springframework.stereotype.Service;

@Service
public class SaveCustomerBalance implements ISaveCustomerBalance {

    private final ICustomerBalanceRepository customerBalanceRepository;

    public SaveCustomerBalance(final ICustomerBalanceRepository customerBalanceRepository) {
        this.customerBalanceRepository = customerBalanceRepository;
    }

    @Override
    public CustomerBalance execute(final CustomerBalance customerBalance) {
        return customerBalanceRepository.save(customerBalance);
    }
}
