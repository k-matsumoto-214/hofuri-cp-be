package com.hofuri.cp.service

import com.hofuri.cp.model.CpInfo
import com.hofuri.cp.repository.database.CpRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.retry.annotation.EnableRetry
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CpRegisterService.class)
@EnableRetry
class CpRegisterServiceSpec extends Specification {

    @Autowired
    CpRegisterService cpRegisterService

    @SpringBean
    CpRepository cpRepository = Mock()

    def "registerCp_DBにCPを登録"() {
        setup:
        1 * cpRepository.upsert(_)

        when:
        cpRegisterService.registerCp(Mock(CpInfo))

        then:
        noExceptionThrown()
    }

    def "registerCp_例外発生時3回リトライ"() {
        setup:
        3 * cpRepository.upsert(_) >> {
            throw new RuntimeException()
        }

        when:
        cpRegisterService.registerCp(Mock(CpInfo))

        then:
        thrown(RuntimeException)
    }
}
