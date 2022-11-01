package com.hofuri.cp.repository.database.impl

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DbUnitConfiguration
import com.hofuri.cp.repository.database.CsvDataSetLoader
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import spock.lang.Specification

import java.time.LocalDate

@MybatisTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
])
@Import(CpRepositoryImpl.class)
class CpRepositoryImplSpec extends Specification {

    @Autowired
    CpRepositoryImpl cpRepository

    @DatabaseSetup(
            value = "/h2/data/cprepository/fetchDailyCpAmountBetweenDate/input",
            type = DatabaseOperation.CLEAN_INSERT
    )
    def "fetchDailyCpAmountBetweenDate_DBから集計できる"() {
        setup:
        def from = LocalDate.of(2022, 10, 21)
        def to = LocalDate.of(2022, 10, 25)

        when:
        def actual = cpRepository.fetchDailyCpAmountBetweenDate(from, to)

        then:
        noExceptionThrown()
        actual.getCpDailyAmounts().get(0).getDate() == from
        actual.getCpDailyAmounts().get(0).getAmountInfos().get(0).amount == 15000
        actual.getCpDailyAmounts().get(0).getAmountInfos().get(0).getName() == "アイエヌジーバンクエヌ・ヴイ"
        actual.getCpDailyAmounts().get(2).getDate() == to
        actual.getCpDailyAmounts().get(2).getAmountInfos().get(3).getAmount() == 0
        actual.getCpDailyAmounts().get(2).getAmountInfos().get(3).getName() == "アサヒグループホールディングス株式会社"
    }

    @DatabaseSetup(
            value = "/h2/data/cprepository/fetchDailyCpAmountBetweenDate/input",
            type = DatabaseOperation.CLEAN_INSERT
    )
    def "fetchDailyCpAmountBetweenDate_DBに集計対象データがない"() {
        setup:
        def from = LocalDate.of(2999, 10, 21)
        def to = LocalDate.of(2999, 10, 25)

        when:
        def actual = cpRepository.fetchDailyCpAmountBetweenDate(from, to)

        then:
        noExceptionThrown()
        actual.getCpDailyAmounts().isEmpty()
    }

    @DatabaseSetup(
            value = "/h2/data/cprepository/fetchTotalCpAmountBetweenDate/input",
            type = DatabaseOperation.CLEAN_INSERT
    )
    def "fetchTotalCpAmountBetweenDate_DBから集計できる"() {
        setup:
        def from = LocalDate.of(2022, 10, 21)
        def to = LocalDate.of(2022, 10, 25)

        when:
        def actual = cpRepository.fetchTotalCpAmountBetweenDate(from, to)

        then:
        noExceptionThrown()
        actual.getCpTotalAmounts().get(0).getDate() == from
        actual.getCpTotalAmounts().get(2).getDate() == to
        actual.getCpTotalAmounts().get(0).getAmount() == 15000
        actual.getCpTotalAmounts().get(2).getAmount() == 6000
    }

    @DatabaseSetup(
            value = "/h2/data/cprepository/fetchTotalCpAmountBetweenDate/input",
            type = DatabaseOperation.CLEAN_INSERT
    )
    def "fetchTotalCpAmountBetweenDate_DBに集計対象データがない"() {
        setup:
        def from = LocalDate.of(2999, 10, 21)
        def to = LocalDate.of(2999, 10, 25)

        when:
        def actual = cpRepository.fetchTotalCpAmountBetweenDate(from, to)

        then:
        noExceptionThrown()
        actual.getCpTotalAmounts().isEmpty()
    }
}
