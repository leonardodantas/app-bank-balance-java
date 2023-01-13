package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IFindUserBalanceEntry;
import com.bank.balance.infra.http.jsons.responses.CustomerBalanceResponse;
import com.bank.balance.infra.http.jsons.responses.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@Api(tags = "Busca o saldo do cliente")
public class FindUserBalanceEntryController {

    private final IFindUserBalanceEntry findUserBalanceEntry;

    public FindUserBalanceEntryController(final IFindUserBalanceEntry findUserBalanceEntry) {
        this.findUserBalanceEntry = findUserBalanceEntry;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("customer/{customerId}/balance")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Success", response = CustomerBalanceResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Error", response = ErrorResponse.class)
    })
    public CustomerBalanceResponse execute(@PathVariable final String customerId) {
        final var response = findUserBalanceEntry.execute(customerId);
        return CustomerBalanceResponse.from(response);
    }
}
