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
        actual.getDates() == List.of(
                LocalDate.of(2022, 10, 21),
                LocalDate.of(2022, 10, 24),
                LocalDate.of(2022, 10, 25)
        )
        actual.getCpDailyAmounts().get(0).getIssureName() == "アイエヌジーバンクエヌ・ヴイ"
        actual.getCpDailyAmounts().get(0).getAmounts() == List.of(15000, 15000, 15000)

        actual.getCpDailyAmounts().get(3).getIssureName() == "アサヒグループホールディングス株式会社"
        actual.getCpDailyAmounts().get(3).getAmounts() == List.of(10000, 10000, 0)
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
