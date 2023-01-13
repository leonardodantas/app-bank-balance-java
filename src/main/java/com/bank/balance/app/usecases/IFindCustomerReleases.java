package com.bank.balance.app.usecases;

import com.bank.balance.infra.http.jsons.responses.BalanceEntryResponse;

import java.time.LocalDate;
import java.util.List;

public interface IFindCustomerReleases {

    List<BalanceEntryResponse> execute(final String customerId, final String startDate, final LocalDate endDate);
}
