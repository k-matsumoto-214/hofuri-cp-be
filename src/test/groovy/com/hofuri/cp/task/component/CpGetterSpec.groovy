package com.hofuri.cp.task.component

import com.hofuri.cp.config.HofuriWebConfig
import com.hofuri.cp.config.WebDriverConfig
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.retry.annotation.EnableRetry
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CpGetter.class)
@EnableRetry
class CpGetterSpec extends Specification {

    @Autowired
    CpGetter cpGetter

    @SpringBean
    HofuriWebConfig hofuriWebConfig = Mock()

    @SpringBean
    WebDriverConfig webDriverConfig = Mock()

    def "getCpInfoList_正常"() {
        setup:
        1 * webDriverConfig.getWebDriver() >> Mock(RemoteWebDriver) {
            findElement(_ as By) >> Mock(WebElement) {
                2 * getText() >>> ["date", "cpNumber"]
                20 * getText() >>> ["name0", "isinCode0", "bondUnit", "amount",
                                    "name", "isinCode", "bondUnit", "amount",
                                    "name", "isinCode", "bondUnit", "amount",
                                    "name", "isinCode", "bondUnit", "amount",
                                    "name4", "isinCode4", "bondUnit", "amount"]
            }
        }
        1 * hofuriWebConfig.firstCpPageUrl() >> "url"
        1 * hofuriWebConfig.dateSelector() >> Mock(By)
        1 * hofuriWebConfig.parseDate(_) >> LocalDate.of(2099, 12, 10)
        1 * hofuriWebConfig.cpNumberSelector() >> Mock(By)
        1 * hofuriWebConfig.parseCpNumber(_) >> 5
        1 * hofuriWebConfig.numberInPage() >> 3
        2 * hofuriWebConfig.cpPageUrl(_) >> 1
        5 * hofuriWebConfig.nameSelector(_) >> Mock(By)
        5 * hofuriWebConfig.isinCodeSelector(_) >> Mock(By)
        5 * hofuriWebConfig.parseIssuerCode(_) >> "issuer"
        5 * hofuriWebConfig.bondUnitSelector(_) >> Mock(By)
        5 * hofuriWebConfig.parseBondUnit(_) >> 50
        5 * hofuriWebConfig.amountSelector(_) >> Mock(By)
        5 * hofuriWebConfig.parseCpAmount(_) >> 500

        when:
        def actual = cpGetter.getCpInfoList()

        then:
        actual.getCpInfos().size() == 5
        actual.getCpInfos().get(0).getDate() == LocalDate.of(2099, 12, 10)
        actual.getCpInfos().get(0).getName() == "name0"
        actual.getCpInfos().get(0).getIsinCode() == "isinCode0"
        actual.getCpInfos().get(0).getIssuerCode() == "issuer"
        actual.getCpInfos().get(0).getBondUnit() == 50
        actual.getCpInfos().get(0).getAmount() == 500

        actual.getCpInfos().get(4).getName() == "name4"
        actual.getCpInfos().get(4).getIsinCode() == "isinCode4"

    }

    def "getCpInfoList_例外発生3回リトライ"() {
        setup:
        3 * webDriverConfig.getWebDriver() >> Mock(RemoteWebDriver)
        3 * hofuriWebConfig.firstCpPageUrl() >> {
            throw new RuntimeException()
        }

        when:
        def actual = cpGetter.getCpInfoList()

        then:
        thrown(RuntimeException)

    }
}
