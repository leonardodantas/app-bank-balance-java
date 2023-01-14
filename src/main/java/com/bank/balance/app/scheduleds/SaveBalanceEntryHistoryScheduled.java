package com.bank.balance.app.scheduleds;

import com.bank.balance.app.usecases.ISaveBalanceEntryHistory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SaveBalanceEntryHistoryScheduled {

    private final ISaveBalanceEntryHistory balanceEntryHistory;
    private static final String TIME_ZONE = "America/Sao_Paulo";

    public SaveBalanceEntryHistoryScheduled(final ISaveBalanceEntryHistory balanceEntryHistory) {
        this.balanceEntryHistory = balanceEntryHistory;
    }

    @Scheduled(cron = "0 23 * * * *", zone = TIME_ZONE)
    public void execute() {
        balanceEntryHistory.execute();
    }
}
