package com.bank.balance.app.usecases.impl

import com.bank.balance.app.exceptions.ExistingTransactionsException
import com.bank.balance.app.exceptions.TransactionIdFoundException
import com.bank.balance.app.exceptions.TransactionTypeInvalidException
import com.bank.balance.app.repositories.ICustomerBalanceRepository
import com.bank.balance.app.repositories.ITransactionRepository
import com.bank.balance.domain.CustomerBalance
import com.bank.balance.domain.Transaction
import com.bank.balance.domain.UserBalanceEntry
import com.bank.balance.domain.UsersBalancesEntriesAdapter
import com.bank.balance.utils.GetMockJson
import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Specification

class EnterBalanceEntrySpecification extends Specification {

    def transactionRepository = Mock(ITransactionRepository)
    def customerBalanceRepository = Mock(ICustomerBalanceRepository)
    def enterBalanceEntries = new EnterBalanceEntries(transactionRepository, customerBalanceRepository)

    def getMockJson = new GetMockJson()

    def "shouldRunFourSingleUserTransactions"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-quatro-transacoes-validas", UserBalanceEntry.class)

        and: "when there is no json transaction already stored in the database"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "there is no user with the balance saved in the database"
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()

        when: "run the enterBalanceEntries use case"
        def response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "response must be different from null"
        assert response != null

        and: "save user balance list of size one, one time"
        1 * customerBalanceRepository.save(_ as List<CustomerBalance>) >> {
            List<CustomerBalance> expected ->
                assert 1 == expected.size()
        }
    }

    def "shouldPerformTransactionForUser"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-uma-transacao-valida", UserBalanceEntry.class)

        and: "when there is no json transaction already stored in the database"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "there is no user with the balance saved in the database"
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()

        when: "run the enterBalanceEntries use case"
        def response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "response must be different from null"
        assert response != null

        and: "save user balance list of size one, one time"
        1 * customerBalanceRepository.save(_ as List<CustomerBalance>) >> {
            List<CustomerBalance> expected ->
                assert 1 == expected.size()
        }
    }

    def "shouldExecuteTheTransactionForTheUserWhenThereAlreadyBalanceSavedDatabase"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-uma-transacao-valida", UserBalanceEntry.class)

        and: "return a list of existing transactions in the database"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "there is no user with the balance saved in the database"
        def customerBalance = getMockJson.execute("saldo-usuario-valido", new TypeReference<List<CustomerBalance>>() {
        })
        customerBalanceRepository.findAllById(_ as List<String>) >> customerBalance

        when: "run the enterBalanceEntries use case"
        def response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "response must be different from null"
        assert response != null

        and: "save user balance list of size one, one time"
        1 * customerBalanceRepository.save(_ as List<CustomerBalance>) >> {
            List<List<CustomerBalance>> expected ->
                assert expected.get(0).get(0).getBalance() == BigDecimal.valueOf(1200)
        }

        1 * transactionRepository.saveAll(_ as List<Transaction>)
    }

    def "shouldThrownTransactionIdFoundException"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-quatro-transacoes-validas-repetidas", UserBalanceEntry.class)

        when: "run the enterBalanceEntries use case"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "thrown TransactionIdFoundException"
        thrown TransactionIdFoundException
    }

    def "shouldThrownExistingTransactionsException"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-quatro-transacoes-validas", UserBalanceEntry.class)

        and: "return a list of existing transactions in the database"
        def transactions = getMockJson.execute("transacoes-armazenadas-banco-de-dados", new TypeReference<List<Transaction>>() {
        })
        transactionRepository.findByTransactionsId(_ as List<String>) >> transactions

        when: "run the enterBalanceEntries use case"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "thrown ExistingTransactionsException"
        thrown ExistingTransactionsException
    }

    def "shouldThrownTransactionTypeInvalidExceptionWithDepositInvalid"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-deposito-invalido", UserBalanceEntry.class)

        when: "run the enterBalanceEntries use case"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "thrown TransactionTypeInvalidException"
        thrown TransactionTypeInvalidException
    }


    def "shouldThrownTransactionTypeInvalidExceptionWithWithdrawInvalid"() {
        given: "a valid userBalanceEntry"
        def userBalanceEntry = getMockJson.execute("usuario-com-saque-invalido", UserBalanceEntry.class)

        when: "run the enterBalanceEntries use case"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "thrown TransactionTypeInvalidException"
        thrown TransactionTypeInvalidException
    }

}
