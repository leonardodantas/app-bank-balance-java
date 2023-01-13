package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IFindCustomerReleases;
import io.swagger.annotations.Api;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Api(tags = "Busca os lançamentos dos clientes menores que 90 dias")
public class FindCustomerReleasesController {

    private final IFindCustomerReleases findCustomerReleases;

    public FindCustomerReleasesController(final IFindCustomerReleases findCustomerReleases) {
        this.findCustomerReleases = findCustomerReleases;
    }

    @GetMapping("customer/{customerId}/releases")
    public void execute(@PathVariable final String customerId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate starDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate) {
        System.out.println(starDate);
    }
}
