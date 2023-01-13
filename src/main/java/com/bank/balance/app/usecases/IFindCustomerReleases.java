package com.bank.balance.app.usecases;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.CustomerRelease;

import java.util.List;

public interface IFindCustomerReleases {

    List<BalanceEntry> execute(final CustomerRelease customerRelease, final int page, final int size);
}
