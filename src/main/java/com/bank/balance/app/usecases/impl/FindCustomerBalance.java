package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.repositories.ICustomerBalanceRepository;
import com.bank.balance.app.usecases.IFindCustomerBalance;
import com.bank.balance.domain.CustomerBalance;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindCustomerBalance implements IFindCustomerBalance {

    private final ICustomerBalanceRepository customerBalanceRepository;

    public FindCustomerBalance(final ICustomerBalanceRepository customerBalanceRepository) {
        this.customerBalanceRepository = customerBalanceRepository;
    }

    @Override
    public Optional<CustomerBalance> execute(final String customerId) {
        return customerBalanceRepository.findById(customerId);
    }
}
