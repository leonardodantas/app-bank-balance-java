package com.bank.balance.infra.http.controllers;

import com.bank.balance.app.usecases.ISaveBalanceEntryHistory;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("save/history")
@Api(tags = "Salvar os lançamentos com mais de 90 dias no MongoDB manualmente")
public class SaveBalanceEntryHistoryController {

    private final ISaveBalanceEntryHistory saveBalanceEntryHistory;

    public SaveBalanceEntryHistoryController(final ISaveBalanceEntryHistory saveBalanceEntryHistory) {
        this.saveBalanceEntryHistory = saveBalanceEntryHistory;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void execute() {
        saveBalanceEntryHistory.execute();
    }
}
