package com.hofuri.cp.presentation.controller

import com.hofuri.cp.model.statistic.daily.CpDailyAmount
import com.hofuri.cp.model.statistic.daily.CpDailyAmountList
import com.hofuri.cp.model.statistic.daily.CpTotalAmount
import com.hofuri.cp.model.statistic.daily.CpTotalAmountList
import com.hofuri.cp.service.CpFindService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CpController.class)
class CpControllerSpec extends Specification {

    @Autowired
    CpController cpController

    @SpringBean
    CpFindService cpFindService = Mock()

    // Spring MVCのモック
    MockMvc mockMvc;

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cpController).build()
    }

    def "GET: /cp/amount/daily"() {
        setup:
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/cp/amount/daily")
                        .queryParam("from", "2022-12-10")
                        .queryParam("to", "2022-12-15")

        1 * cpFindService.fetchDailyCpAmountBetweenDate(*_) >>
                Mock(CpDailyAmountList) {
                    getCpDailyAmounts() >> List.of(
                            Mock(CpDailyAmount) {
                                getDate() >> LocalDate.of(2022, 12, 10)
                                getAmountInfos() >> List.of(
                                        Mock(CpDailyAmount.AmountInfo) {
                                            getName() >> "test発行体"
                                            getAmount() >> 5000
                                        }
                                )
                            }
                    )
                }

        when:
        def actual = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()

        then:
        actual.getContentAsString(StandardCharsets.UTF_8) ==
                "{\"cpDailyAmountDatas\":" +
                "[{\"date\":\"2022-12-10\"," +
                "\"cpAmountData\":" + "[{\"name\":\"test発行体\",\"amount\":5000}]}]}"
    }

    def "GET: /cp/amount/daily_バリデーション_日付の期間が不正"() {
        setup:
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/cp/amount/daily")
                        .queryParam("from", "2022-12-16")
                        .queryParam("to", "2022-12-15")

        0 * cpFindService.fetchDailyCpAmountBetweenDate(*_)

        when:
        def actual = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()

        then:
        noExceptionThrown()
    }

    def "GET: /cp/amount/daily_バリデーション_日付形式が不正"() {
        setup:
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/cp/amount/daily")
                        .queryParam("from", "202212-16")
                        .queryParam("to", "2022-1215")

        0 * cpFindService.fetchDailyCpAmountBetweenDate(*_)

        when:
        def actual = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()

        then:
        noExceptionThrown()
    }

    def "GET: /cp/amount/total"() {
        setup:
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/cp/amount/total")
                        .queryParam("from", "2022-12-10")
                        .queryParam("to", "2022-12-15")

        1 * cpFindService.fetchTotalCpAmountBetweenDate(*_) >>
                Mock(CpTotalAmountList) {
                    getCpTotalAmounts() >> List.of(
                            Mock(CpTotalAmount) {
                                getDate() >> LocalDate.of(2022, 12, 10)
                                getAmount() >> 5000
                            }
                    )
                }

        when:
        def actual = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()

        then:
        actual.getContentAsString(StandardCharsets.UTF_8) ==
                "{\"cpTotalAmountDatas\":" +
                "[{\"date\":\"2022-12-10\"," +
                "\"amount\":5000}]}"
    }

    def "GET: /cp/amount/total_バリデーション_日付の期間が不正"() {
        setup:
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/cp/amount/total")
                        .queryParam("from", "2022-12-16")
                        .queryParam("to", "2022-12-15")

        0 * cpFindService.fetchTotalCpAmountBetweenDate(*_)

        when:
        def actual = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()

        then:
        noExceptionThrown()
    }

    def "GET: /cp/amount/total_バリデーション_日付形式が不正"() {
        setup:
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/cp/amount/total")
                        .queryParam("from", "202212-16")
                        .queryParam("to", "2022-1215")

        0 * cpFindService.fetchTotalCpAmountBetweenDate(*_)

        when:
        def actual = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResponse()

        then:
        noExceptionThrown()
    }
}
