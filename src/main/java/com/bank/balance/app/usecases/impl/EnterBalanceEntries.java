package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.usecases.IEnterBalanceEntries;
import com.bank.balance.domain.UserBalanceEntries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnterBalanceEntries implements IEnterBalanceEntries {

    /**
     * VERIFICAR SE EXISTEM TRANSAÇÕES REPETIDAS
     * VERIFICAR SE AS TRANSAÇÕES SÃO NOVAS (NÃO PERMITIR TRANSAÇÕES REPETIDAS)
     * ATUALIZAR SALDO DO USUARIO
     *
     * @param userBalanceEntries
     * @return
     */
    @Override
    public UserBalanceEntries execute(final UserBalanceEntries userBalanceEntries) {
        log.info("Initialized execute user balance entries");
        final var nonRepeatTransactionsId = userBalanceEntries.getNonRepeatTransactionsId();
        final var transactionsId = userBalanceEntries.getTransactionsId();
        final var transactionsIdFounds = new ArrayList<String>();

        if (nonRepeatTransactionsId.size() != transactionsId.size()) {
            final var transactionsIdSorted = transactionsId.stream().sorted().collect(Collectors.toUnmodifiableList());

            transactionsIdSorted.forEach(transactionId -> {
                transactionsIdSorted.stream().filter(transactionIdSorted -> transactionIdSorted.equalsIgnoreCase(transactionId)).findFirst()
                        .ifPresent(transactionsIdFounds::add);
            });

            throw new TransactionIdFoundException(transactionsIdFounds);
        }
        return null;
    }

    /**
     * VERIFICAR SE EXISTEM TRANSAÇÕES REPETIDAS
     * VERIFICAR SE AS TRANSAÇÕES SÃO NOVAS (NÃO PERMITIR TRANSAÇÕES REPETIDAS)
     * ATUALIZAR SALDO DO USUARIO
     *
     * @param userBalanceEntries
     * @return
     */
    @Override
    public List<UserBalanceEntries> execute(final List<UserBalanceEntries> userBalanceEntries) {
        log.info("Initialized execute list of user balance entries");
        return null;
    }
}
