package com.hofuri.cp.task

import com.hofuri.cp.model.CpInfo
import com.hofuri.cp.model.CpInfoList
import com.hofuri.cp.service.CpRegisterService
import com.hofuri.cp.task.component.CpGetter
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GetCpBalanceTask.class)
class GetCpBalanceTaskSpec extends Specification {
    @Autowired
    GetCpBalanceTask getCpBalanceTask

    @SpringBean
    CpGetter cpGetter = Mock()

    @SpringBean
    CpRegisterService cpRegisterService = Mock()

    def "getCpBalance()_正常"() {
        setup:
        1 * cpGetter.getCpInfoList() >> Mock(CpInfoList) {
            getCpInfos() >> List.of(Mock(CpInfo), Mock(CpInfo))
        }
        2 * cpRegisterService.registerCp(_)

        when:
        getCpBalanceTask.getCpBalance()

        then:
        noExceptionThrown()
    }

    def "getCpBalance()_異常"() {
        setup:
        1 * cpGetter.getCpInfoList() >> Mock(CpInfoList) {
            getCpInfos() >> List.of(Mock(CpInfo), Mock(CpInfo))
        }
        1 * cpRegisterService.registerCp(_)
        1 * cpRegisterService.registerCp(_) >> {
            throw new RuntimeException()
        }

        when:
        getCpBalanceTask.getCpBalance()

        then:
        noExceptionThrown()
    }


}
