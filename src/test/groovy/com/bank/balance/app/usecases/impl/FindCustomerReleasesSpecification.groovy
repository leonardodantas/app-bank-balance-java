package com.bank.balance.app.usecases.impl

import com.bank.balance.app.exceptions.DaysSearchException
import com.bank.balance.app.exceptions.IncompatibleDatesException
import com.bank.balance.app.repositories.IBalanceEntryRepository
import com.bank.balance.domain.BalanceEntry
import com.bank.balance.domain.CustomerRelease
import com.bank.balance.utils.GetMockJson
import com.fasterxml.jackson.core.type.TypeReference
import spock.lang.Specification

import java.time.LocalDate

class FindCustomerReleasesSpecification extends Specification {

    def balanceEntryRepository = Mock(IBalanceEntryRepository)
    def findCustomerReleases = new FindCustomerReleases(balanceEntryRepository)

    def getMockJson = new GetMockJson()

    def "shouldThrownIncompatibleDatesException"() {
        given: "a customerRelease with incompatible dates"
        def customerRelease = getMockJson.execute("customer-release-data-final-menor-que-data-inicial", CustomerRelease)

        when: "run findCustomerReleases with customerRelease, page and size"
        findCustomerReleases.execute(customerRelease, 0, 1)

        then: "thrown IncompatibleDatesException"
        thrown IncompatibleDatesException
    }

    def "shouldThrownDaysSearchException"() {
        given: "customerRelease with endDate minus minDate"
        def customerRelease = getMockJson.execute("customer-release-data-inicial-menor-que-data-minima-permitida", CustomerRelease)

        when: "run findCustomerReleases with customerRelease, page and size"
        findCustomerReleases.execute(customerRelease, 0, 1)

        then: "thrown DaysSearchException"
        thrown DaysSearchException
    }

    def "shouldExecuteWithSuccess"() {
        given: "a valid customer release"
        def customerRelease = new CustomerRelease("1", LocalDate.now().minusDays(30), LocalDate.now())

        and: "a mock for balanceEntryRepositories"
        def balanceEntries = getMockJson.execute("lancamentos-usuario", new TypeReference<List<BalanceEntry>>() {
        })
        balanceEntryRepository.findBy(_ as CustomerRelease, _ as Integer, _ as Integer) >> balanceEntries

        when: "run findCustomerReleases with customerRelease, page and size"
        def result = findCustomerReleases.execute(customerRelease, 0, 1)

        then: "result must be different from null and size equals 3"
        assert result != null
        assert result.size() == 3
    }
}
