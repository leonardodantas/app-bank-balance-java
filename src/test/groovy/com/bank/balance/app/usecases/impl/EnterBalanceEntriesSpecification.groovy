package com.bank.balance.app.usecases.impl

import com.bank.balance.app.repositories.ICustomerBalanceRepository
import com.bank.balance.app.repositories.ITransactionRepository
import com.bank.balance.domain.CustomerBalance
import com.bank.balance.domain.UserBalanceEntry
import com.bank.balance.domain.UsersBalancesEntriesAdapter
import com.bank.balance.utils.GetMockJson
import spock.lang.Specification

class EnterBalanceEntriesSpecification extends Specification {


    def transactionRepository = Mock(ITransactionRepository)
    def customerBalanceRepository = Mock(ICustomerBalanceRepository)
    def enterBalanceEntries = new EnterBalanceEntries(transactionRepository, customerBalanceRepository)

    def getMockJson = new GetMockJson()

    /**
     * deveExecutarQuatroTransacoesDeUmUnicoUsuario
     * deve Lançar Exceção quando existir transações repetidas na lista
     * deve lançar exceções quando alguma transação ja existir no banco de dados
     */

    def "deveExecutarQuatroTransacoesDeUmUnicoUsuario"(){
        given: "um userBalanceEntry valido"
        def userBalanceEntry = getMockJson.execute("usuario-com-quatro-transacoes-validas", UserBalanceEntry.class)

        and: "quando não existir nenhuma transação do json ja armazenada no banco de dados"
        transactionRepository.findByTransactionsId(_ as List<String>) >> List.of()

        and: "não existir nenhum usuario com o saldo salvo no banco de dados"
        customerBalanceRepository.findAllById(_ as List<String>) >> List.of()

        when: "executar o caso de uso enterBalanceEntries"

        def response = enterBalanceEntries.execute(UsersBalancesEntriesAdapter.from(userBalanceEntry))

        then: "response deve ser diferente de null"
        assert response != null

        and: "salvar a lista de saldo do usuario uma unica vez"
        1 * customerBalanceRepository.save(_ as List<CustomerBalance>)
    }

}
