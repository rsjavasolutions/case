package com.example.demo.cases

import com.example.demo.cases.enums.CaseCategory
import com.example.demo.cases.enums.CaseGenus
import com.example.demo.cases.enums.Status
import com.example.demo.cases.test.postgres.PostgresSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import test.annotation.IntegrationTest

import java.time.LocalDate

@IntegrationTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Sql([
        '/scripts/sql/truncate_tables.sql',
        '/scripts/sql/add_cases.sql'
])
class CaseServiceIntegrationSpec extends PostgresSpecification {

    @Autowired
    CaseService caseService;

    def 'should return correct data by uuid'() {
        given: 'uuid'
        def uuid = "c61c2ce8-30c5-4e59-acf7-caf576f286bb"

        when: 'try find case by uuid'
        def result = caseService.getCaseByUuid(uuid)

        then: 'result is correct'
        with(result) {
            uuid == 'c61c2ce8-30c5-4e59-acf7-caf576f286bb'
            caseGenus == CaseGenus.AUTOMATIC
            category == CaseCategory.EVENTS
            comment == 'some_comment_2'
            content == 'some_content_2'
            status == Status.UNREALIZED
            contractualVerificationDate == LocalDate.of(2041, 6, 1)
            realizationDate == null
        }
    }
}

