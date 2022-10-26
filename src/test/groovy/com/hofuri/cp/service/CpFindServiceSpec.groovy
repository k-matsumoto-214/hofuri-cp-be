package com.hofuri.cp.service

import com.hofuri.cp.model.statistic.daily.CpDailyAmountList
import com.hofuri.cp.repository.database.CpRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.retry.annotation.EnableRetry
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CpFindService.class)
@EnableRetry
class CpFindServiceSpec extends Specification {

    @Autowired
    CpFindService cpFindService

    @SpringBean
    CpRepository cpRepository = Mock()

    def "fetchDailyCpAmountBetweenDate_正常"() {
        setup:
        1 * cpRepository.fetchDailyCpAmountBetweenDate(*_) >> Mock(CpDailyAmountList)

        when:
        def actual =
                cpFindService.fetchDailyCpAmountBetweenDate(
                        GroovyMock(LocalDate),
                        GroovyMock(LocalDate))

        then:
        noExceptionThrown()

    }

    def "fetchDailyCpAmountBetweenDate_例外発生_3回リトライ"() {
        setup:
        3 * cpRepository.fetchDailyCpAmountBetweenDate(*_) >> {
            throw new RuntimeException()
        }

        when:
        def actual =
                cpFindService.fetchDailyCpAmountBetweenDate(
                        GroovyMock(LocalDate),
                        GroovyMock(LocalDate))

        then:
        thrown(RuntimeException)

    }


}
