package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IFindCustomerReleases;
import com.bank.balance.infra.http.jsons.responses.BalanceEntryResponse;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Busca os lançamentos dos clientes feitos a 90 dias")
public class FindCustomerReleasesController {

    private final IFindCustomerReleases findCustomerReleases;

    public FindCustomerReleasesController(final IFindCustomerReleases findCustomerReleases) {
        this.findCustomerReleases = findCustomerReleases;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("customer/{customerId}/releases")
    public List<BalanceEntryResponse> execute(
            @PathVariable final String customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate starDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "20") final int size
    ) {
        final var response = findCustomerReleases.execute(customerId, starDate, endDate, page, size);
        return response.stream().map(BalanceEntryResponse::from).collect(Collectors.toUnmodifiableList());
    }
}
