package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IFindCustomerReleasesHistory;
import com.bank.balance.domain.CustomerRelease;
import com.bank.balance.infra.http.jsons.responses.BalanceEntryResponse;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Busca os lançamentos dos clientes feitos a mais de 90 dias")
public class FindCustomerReleasesHistoryController {

    private final IFindCustomerReleasesHistory findCustomerReleasesHistory;

    public FindCustomerReleasesHistoryController(final IFindCustomerReleasesHistory findCustomerReleasesHistory) {
        this.findCustomerReleasesHistory = findCustomerReleasesHistory;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("customer/{customerId}/releases/history")
    public List<BalanceEntryResponse> execute(
            @PathVariable final String customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate starDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "20") final int size
    ) {
        final var customerRelease = new CustomerRelease(customerId, starDate, endDate);
        final var response = findCustomerReleasesHistory.execute(customerRelease, page, size);
        return response.stream().map(BalanceEntryResponse::from).collect(Collectors.toUnmodifiableList());
    }
}
