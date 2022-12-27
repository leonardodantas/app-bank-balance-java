package com.bank.balance.app.usecases.impl;

import com.bank.balance.app.exceptions.ExistingTransactionsException;
import com.bank.balance.app.exceptions.TransactionIdFoundException;
import com.bank.balance.app.repositories.ITransactionRepository;
import com.bank.balance.app.usecases.*;
import com.bank.balance.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnterBalanceEntries implements IEnterBalanceEntries {

    private final ITransactionRepository transactionRepository;
    private final IFindCustomerBalance findCustomerBalance;
    private final ISaveCustomerBalance saveCustomerBalance;
    private final ISaveTransactions saveTransactions;
    private final ISaveUserBalanceEntries saveUserBalanceEntries;

    public EnterBalanceEntries(final ITransactionRepository transactionRepository, final IFindCustomerBalance findCustomerBalance, final ISaveCustomerBalance saveCustomerBalance, final ISaveTransactions saveTransactions, final ISaveUserBalanceEntries saveUserBalanceEntries) {
        this.transactionRepository = transactionRepository;
        this.findCustomerBalance = findCustomerBalance;
        this.saveCustomerBalance = saveCustomerBalance;
        this.saveTransactions = saveTransactions;
        this.saveUserBalanceEntries = saveUserBalanceEntries;
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
    public UserBalanceEntries execute(final UserBalanceEntries userBalanceEntries) {
        log.info("Initialized execute user balance entries");

        /**
         * VERIFICANDO SE EXISTEM ALGUM TRANSAÇÃO REPETIDA NA LISTA
         */
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

        /**
         * VERIFICAR SE ALGUMA TRANSAÇÃO JÁ EXISTE NO BANCO DE DADOS
         */
        final var existingTransactions = transactionRepository.findByTransactionId(transactionsId);

        if (!existingTransactions.isEmpty()) {
            throw new ExistingTransactionsException(existingTransactions.stream().map(Transaction::getTransactionId).collect(Collectors.toUnmodifiableList()));
        }

        final var customerTransactions = userBalanceEntries.getBalanceEntries().stream().map(Transaction::from).collect(Collectors.toUnmodifiableList());
        saveTransactions.execute(customerTransactions);

        /**
         * Verificar qual valor vai ser adicionado ao saldo do usuario
         */
        final var balanceToAdd = userBalanceEntries.getBalanceEntries().stream()
                .filter(balanceEntry -> balanceEntry.getTransactionType().equals(TransactionType.DEPOSIT))
                .map(BalanceEntry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final var balanceForWithdrawal = userBalanceEntries.getBalanceEntries().stream()
                .filter(balanceEntry -> balanceEntry.getTransactionType().equals(TransactionType.WITHDRAW))
                .map(BalanceEntry::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final var balance = balanceToAdd.add(balanceForWithdrawal);

        findCustomerBalance.execute(userBalanceEntries.getCustomerId())
                .ifPresentOrElse((customerBalance) -> {
                    final var existingBalance = customerBalance.getBalance();
                    final var customerBalanceToUpdate = CustomerBalance.of(userBalanceEntries.getCustomerId(), existingBalance.add(balance));
                    saveCustomerBalance.execute(customerBalanceToUpdate);
                }, () -> {
                    final var customerBalanceToSave = CustomerBalance.of(userBalanceEntries.getCustomerId(), balance);
                    saveCustomerBalance.execute(customerBalanceToSave);
                });

        /**
         * Adicionar lançamentos na tabela temporaria por noventa dias
         */

        return saveUserBalanceEntries.execute(userBalanceEntries);
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
