package com.bank.balance.app.usecases.impl

import com.bank.balance.app.exceptions.ExistingTransactionsException
import com.bank.balance.app.exceptions.TransactionIdFoundException
import com.bank.balance.app.exceptions.TransactionTypeInvalidException
import com.bank.balance.app.repositories.ICustomerBalanceEntityRepository
import com.bank.balance.app.repositories.ITransactionEntityRepository
import com.bank.balance.app.repositories.IUserBalanceEntryEntityRepository
import com.bank.balance.domain.CustomerBalance
import com.bank.balance.domain.Transaction
import com.bank.balance.domain.UserBalanceEntry
import com.bank.balance.domain.UsersBalancesEntriesAdapter
import com.bank.balance.utils.GetMockJson
import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Specification

class EnterBalanceEntriesSpecification extends Specification {

    def transactionRepository = Mock(ITransactionEntityRepository)
    def customerBalanceRepository = Mock(ICustomerBalanceEntityRepository)
    def userBalanceEntryRepository = Mock(IUserBalanceEntryEntityRepository)
    def enterBalanceEntries = new EnterBalanceEntries(transactionRepository, customerBalanceRepository, userBalanceEntryRepository)

    def getMockJson = new GetMockJson()

    def "shouldRunMultipleUsersWithMultipleTransactions"() {
        given: "a valid userBalanceEntries list"
        def userBalanceEntry = getMockJson.execute("usuarios-com-transacoes-validas", new TypeReference<List<UserBalanceEntry>>() {
        })

        and: "when there is no json transaction already stored in the database"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "there is no user with the balance saved in the database"
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()

        and: "return userBalanceEntry after save List of UserBalanceEntry"
        userBalanceEntryRepository.save(_ as List<UserBalanceEntry>) >> userBalanceEntry

        when: "execute enterBalanceEntries"
        def result = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "result size equals 3"
        assert result.getCustomersIds().size() == 3

        and: "save user balance list of size 3"
        1 * customerBalanceRepository.save(_ as List<CustomerBalance>) >> {
            List<List<CustomerBalance>> expected ->
                assert expected.get(0).size() == 3
        }

    }

    def "shouldRunMultipleUsersWithSplitTransactions"() {
        given: "a valid userBalanceEntries list"
        def userBalanceEntry = getMockJson.execute("usuarios-com-transacoes-validas-divididas", new TypeReference<List<UserBalanceEntry>>() {
        })

        and: "when there is no json transaction already stored in the database"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "there is no user with the balance saved in the database"
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()

        and: "return userBalanceEntry after save List of UserBalanceEntry"
        userBalanceEntryRepository.save(_ as List<UserBalanceEntry>) >> userBalanceEntry

        when: "execute enterBalanceEntries"
        def result = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "result size equals 4"
        assert result.getCustomersIds().size() == 4

        and: "save user balance list of size 3"
        1 * customerBalanceRepository.save(_ as List<CustomerBalance>) >> {
            List<List<CustomerBalance>> expected ->
                assert expected.get(0).size() == 3
        }

        and: "save transactions list of size 12"
        1 * transactionRepository.saveAll(_ as List<Transaction>) >> {
            List<List<Transaction>> expected ->
                assert expected.get(0).size() == 12
        }
    }

    def "shouldThrowTransactionTypeInvalidException"() {
        given: "a valid userBalanceEntries list"
        def userBalanceEntry = getMockJson.execute("usuarios-com-saques-invalidos", new TypeReference<List<UserBalanceEntry>>() {
        })

        when: "execute enterBalanceEntries"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "thrown TransactionTypeInvalidException"
        thrown TransactionTypeInvalidException
    }

    def "shouldThrowTransactionIdFoundException"() {
        given: "a valid userBalanceEntries list"
        def userBalanceEntry = getMockJson.execute("usuarios-com-transacoes-repetidas", new TypeReference<List<UserBalanceEntry>>() {
        })

        when: "execute enterBalanceEntries"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "thrown TransactionTypeInvalidException"
        thrown TransactionIdFoundException
    }

    def "shouldThrowExistingTransactionsException"() {
        given: "a valid userBalanceEntries list"
        def userBalanceEntry = getMockJson.execute("usuarios-com-transacoes-validas", new TypeReference<List<UserBalanceEntry>>() {
        })

        and: "return a list of existing transactions in the database"
        def transactions = getMockJson.execute("transacoes-armazenadas-banco-de-dados", new TypeReference<List<Transaction>>() {
        })
        transactionRepository.findByTransactionsId(_ as List<String>) >> transactions

        when: "execute enterBalanceEntries"
        enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "throw ExistingTransactionsException"
        thrown ExistingTransactionsException
    }
}