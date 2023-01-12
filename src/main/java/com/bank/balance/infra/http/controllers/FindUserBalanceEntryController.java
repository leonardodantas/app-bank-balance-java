package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IFindUserBalanceEntry;
import com.bank.balance.infra.http.jsons.responses.CustomerBalanceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindUserBalanceEntryController {

    private final IFindUserBalanceEntry findUserBalanceEntry;

    public FindUserBalanceEntryController(final IFindUserBalanceEntry findUserBalanceEntry) {
        this.findUserBalanceEntry = findUserBalanceEntry;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("customer/{customerId}/balance")
    public CustomerBalanceResponse execute(@PathVariable final String customerId){
        final var response = findUserBalanceEntry.execute(customerId);
        return CustomerBalanceResponse.from(response);
    }
}
