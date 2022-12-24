package com.bank.balance.infra.http.controllers;

import com.bank.balance.infra.http.converters.UserBalanceEntriesConverter;
import com.bank.balance.infra.http.converters.UserBalanceEntryConverter;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "Enter Balance Entries")
@RequestMapping("perform/launches")
public class EnterBalanceEntriesByFileController {

    private final UserBalanceEntryConverter userBalanceEntryConverter;
    private final UserBalanceEntriesConverter userBalanceEntriesConverter;

    public EnterBalanceEntriesByFileController(final UserBalanceEntryConverter userBalanceEntryConverter, final UserBalanceEntriesConverter userBalanceEntriesConverter) {
        this.userBalanceEntryConverter = userBalanceEntryConverter;
        this.userBalanceEntriesConverter = userBalanceEntriesConverter;
    }

    @PostMapping("user/{customerId}")
    public void execute(@RequestParam("file") @RequestPart final MultipartFile request, @PathVariable final String customerId) {
        final var userBalanceEntries = userBalanceEntryConverter.toDomain(customerId).convert(request);
        System.out.println(userBalanceEntries);
    }

    @PostMapping("all")
    public void execute(@RequestParam("file") @RequestPart final MultipartFile request) {
        final var userBalanceEntries = userBalanceEntriesConverter.convert(request);
        System.out.println(userBalanceEntries);
    }
}
