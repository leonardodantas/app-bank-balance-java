package com.bank.balance.infra.http.controllers;

import com.bank.balance.infra.http.converters.UserBalanceEntryConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("perform/launches")
public class EnterBalanceEntriesByFileController {

    private final UserBalanceEntryConverter userBalanceEntryConverter;

    public EnterBalanceEntriesByFileController(final UserBalanceEntryConverter userBalanceEntryConverter) {
        this.userBalanceEntryConverter = userBalanceEntryConverter;
    }

    @PostMapping
    public void execute(@RequestPart final MultipartFile request, @RequestParam final String customerId) {
        final var userBalanceEntries = userBalanceEntryConverter.toDomain(customerId).convert(request);

    }

    @PostMapping
    public void execute(@RequestPart final MultipartFile request) {

    }
}
