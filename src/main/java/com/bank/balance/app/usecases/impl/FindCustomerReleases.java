package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.usecases.IFindCustomerReleases;
import com.bank.balance.infra.http.jsons.responses.BalanceEntryResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FindCustomerReleases implements IFindCustomerReleases {

    //findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual
    @Override
    public List<BalanceEntryResponse> execute(final String customerId, final String startDate, final LocalDate endDate) {
        return null;
    }
}
