package com.bank.balance.app.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RepeatTransactionsUtil {

    private static final List<String> transactionsIdFounds = new ArrayList<>();

    public static List<String> getRepeatTransactionsId(final List<String> transactionsId){
        transactionsId.forEach(transactionId -> {
            transactionsId.stream().filter(transactionIdSorted -> transactionIdSorted.equalsIgnoreCase(transactionId)).findFirst()
                    .ifPresent(transactionsIdFounds::add);
        });
        return transactionsIdFounds;
    }
}
