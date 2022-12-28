package com.bank.balance.infra.database.postgres.converters;

import com.bank.balance.domain.CustomerBalance;
import com.bank.balance.infra.database.postgres.entities.CustomerBalanceEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerBalanceEntityToCustomerBalance implements Converter<CustomerBalanceEntity, CustomerBalance> {

    @Override
    public CustomerBalance convert(final CustomerBalanceEntity entity) {
        return CustomerBalance.of(entity.getCustomerId(), entity.getBalance(), entity.getLastUpdate());
    }
}
