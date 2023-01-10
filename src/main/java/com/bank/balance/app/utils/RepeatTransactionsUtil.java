package com.bank.balance.app.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RepeatTransactionsUtil {

    private static final List<String> transactionsIdFounds = new ArrayList<>();

    public static List<String> getRepeatTransactionsId(final List<String> transactionsId) {
        final var quantityRepeating = new HashMap<String, Integer>();

        transactionsId.forEach(transactionId -> {
            final var quantity = Optional.ofNullable(quantityRepeating.get(transactionId)).orElse(0);
            quantityRepeating.put(transactionId, quantity + 1);
        });

        quantityRepeating.forEach((transactionId, quantity) -> {
            if (quantity > 1) {
                transactionsIdFounds.add(transactionId);
            }
        });

        return transactionsIdFounds;
    }
}
