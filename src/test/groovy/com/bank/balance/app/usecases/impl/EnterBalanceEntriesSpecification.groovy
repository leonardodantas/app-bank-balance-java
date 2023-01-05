package com.bank.balance.app.usecases.impl


import com.bank.balance.app.repositories.ICustomerBalanceRepository
import com.bank.balance.app.repositories.ITransactionRepository
import com.bank.balance.domain.CustomerBalance
import com.bank.balance.domain.Transaction
import com.bank.balance.domain.UserBalanceEntry
import com.bank.balance.domain.UsersBalancesEntriesAdapter
import com.bank.balance.utils.GetMockJson
import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Specification

class EnterBalanceEntriesSpecification extends Specification {

    def transactionRepository = Mock(ITransactionRepository)
    def customerBalanceRepository = Mock(ICustomerBalanceRepository)
    def enterBalanceEntries = new EnterBalanceEntries(transactionRepository, customerBalanceRepository)

    def getMockJson = new GetMockJson()

    def "shouldRunMultipleUsersWithMultipleTransactions"() {
        given: "a valid userBalanceEntries list"
        def userBalanceEntry = getMockJson.execute("usuarios-com-transacoes-validas", new TypeReference<List<UserBalanceEntry>>() {
        })

        and: "when there is no json transaction already stored in the database"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "there is no user with the balance saved in the database"
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()

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
}