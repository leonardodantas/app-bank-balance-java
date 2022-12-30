package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IEnterBalanceEntries;
import com.bank.balance.app.usecases.IEnterBalanceEntry;
import com.bank.balance.infra.http.converters.UserBalanceEntriesConverter;
import com.bank.balance.infra.http.converters.UserBalanceEntryConverter;
import com.bank.balance.infra.http.jsons.responses.UserBalanceEntriesResponse;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("perform/launches")
@Api(tags = "Enter Balance Entries")
public class EnterBalanceEntriesByFileController {

    private final UserBalanceEntryConverter userBalanceEntryConverter;
    private final UserBalanceEntriesConverter userBalanceEntriesConverter;
    private final IEnterBalanceEntry enterBalanceEntry;
    private final IEnterBalanceEntries enterBalanceEntries;

    public EnterBalanceEntriesByFileController(final UserBalanceEntryConverter userBalanceEntryConverter, final UserBalanceEntriesConverter userBalanceEntriesConverter, final IEnterBalanceEntry enterBalanceEntry, final IEnterBalanceEntries enterBalanceEntries) {
        this.userBalanceEntryConverter = userBalanceEntryConverter;
        this.userBalanceEntriesConverter = userBalanceEntriesConverter;
        this.enterBalanceEntry = enterBalanceEntry;
        this.enterBalanceEntries = enterBalanceEntries;
    }

    @PostMapping("user/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserBalanceEntriesResponse execute(@RequestParam("file") @RequestPart final MultipartFile request, @PathVariable final String customerId) {
        final var userBalanceEntries = userBalanceEntryConverter.toDomain(customerId).convert(request);
        final var response = enterBalanceEntry.execute(userBalanceEntries);
        return UserBalanceEntriesResponse.from(response);
    }

    @PostMapping("all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserBalanceEntriesResponse> execute(@RequestParam("file") @RequestPart final MultipartFile request) {
        final var usersBalanceEntries = userBalanceEntriesConverter.convert(request);
        final var response = enterBalanceEntries.execute(usersBalanceEntries);
        return response.getUserBalanceEntries().stream().map(UserBalanceEntriesResponse::from).collect(Collectors.toUnmodifiableList());
    }
}
