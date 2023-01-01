package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.IEnterBalanceEntries;
import com.bank.balance.domain.UsersBalancesEntriesAdapter;
import com.bank.balance.infra.http.converters.UserBalanceEntriesConverter;
import com.bank.balance.infra.http.converters.UserBalanceEntryConverter;
import com.bank.balance.infra.http.jsons.responses.UserBalanceEntriesResponse;
import com.bank.balance.infra.http.validators.BeanValidation;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("perform/launches")
@Api(tags = "Enter Balance Entries")
public class EnterBalanceEntriesByFileController {

    private final UserBalanceEntryConverter userBalanceEntryConverter;
    private final UserBalanceEntriesConverter userBalanceEntriesConverter;
    private final IEnterBalanceEntries enterBalanceEntries;

    public EnterBalanceEntriesByFileController(final UserBalanceEntryConverter userBalanceEntryConverter, final UserBalanceEntriesConverter userBalanceEntriesConverter, final IEnterBalanceEntries enterBalanceEntries) {
        this.userBalanceEntryConverter = userBalanceEntryConverter;
        this.userBalanceEntriesConverter = userBalanceEntriesConverter;
        this.enterBalanceEntries = enterBalanceEntries;
    }

    @PostMapping("user/{customerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserBalanceEntriesResponse execute(@RequestParam("file") @RequestPart final MultipartFile request, @PathVariable final String customerId) {
        final var userBalanceEntries = userBalanceEntryConverter.toDomain(customerId).convert(request);
        BeanValidation.validate(userBalanceEntries);
        final var response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntries));
        return UserBalanceEntriesResponse.from(response.getOne());
    }

    @PostMapping("all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserBalanceEntriesResponse> execute(@RequestParam("file") @RequestPart final MultipartFile request) {
        final var usersBalanceEntries = userBalanceEntriesConverter.convert(request);
        BeanValidation.validate(usersBalanceEntries);
        final var response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(usersBalanceEntries));
        return response.getUserBalanceEntries().stream().map(UserBalanceEntriesResponse::from).collect(Collectors.toUnmodifiableList());
    }
}
