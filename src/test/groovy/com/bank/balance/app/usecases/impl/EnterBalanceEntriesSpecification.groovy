package com.bank.balance.app.usecases.impl

import com.bank.balance.app.repositories.ICustomerBalanceRepository
import com.bank.balance.app.repositories.ITransactionRepository
import com.bank.balance.domain.UserBalanceEntry
import com.bank.balance.domain.UsersBalancesEntriesAdapter
import com.bank.balance.utils.GetMockJson
import spock.lang.Specification

class EnterBalanceEntriesSpecification extends Specification {


    def transactionRepository = Mock(ITransactionRepository)
    def customerBalanceRepository = Mock(ICustomerBalanceRepository)
    def enterBalanceEntries = new EnterBalanceEntries(transactionRepository, customerBalanceRepository)

    def getMockJson = new GetMockJson()

    def "deveExecutarQuatroTransacoesDeUmUnicoUsuario"(){
        given: "um userBalanceEntry valido"
        def userBalanceEntry = getMockJson.execute("usuario-com-quatro-transacoes-validas", UserBalanceEntry.class)

        and: "não existir nenhuma transação do json ja armazenada no banco de dados"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: ""
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()
        when: "executar o caso de uso enterBalanceEntries"
        def response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "ddd"
    }

}
