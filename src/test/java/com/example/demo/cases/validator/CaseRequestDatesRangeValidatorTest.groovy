package com.example.demo.cases.validator

import com.example.demo.cases.exception.InvalidCaseRequestDatesException
import com.example.demo.cases.exception.InvalidCaseRequestDatesRangeException
import spock.lang.Specification

import java.time.LocalDate

class CaseRequestDatesRangeValidatorTest extends Specification {

    def validator = new CaseRequestDatesRangeValidator()

    def "Should throw exception when to date is before form date"() {
        given:
        def formDate = startDate
        def toDate = endDate

        when:
        validator.validateDates(startDate, endDate)

        then:
        thrown(InvalidCaseRequestDatesException)

        where:
        startDate                 | endDate
        LocalDate.of(2031, 6, 1)  | LocalDate.of(2031, 5, 1)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2031, 7, 14)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2011, 5, 7)
    }

    def "Should not throw exception when to date is before form date"() {
        given:
        def formDate = startDate
        def toDate = endDate

        when:
        validator.validateDates(startDate, endDate)

        then:
        notThrown(InvalidCaseRequestDatesException)

        where:
        startDate                 | endDate
        LocalDate.of(2031, 6, 1)  | LocalDate.of(2031, 6, 2)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2031, 7, 20)
        LocalDate.of(2031, 1, 1)  | LocalDate.of(2031, 1, 7)
    }

    def "Should throw exception when dates range is exceeded"() {
        given:
        def formDate = startDate
        def toDate = endDate

        when:
        validator.validateDates(startDate, endDate)

        then:
        thrown(InvalidCaseRequestDatesRangeException)

        where:
        startDate                 | endDate
        LocalDate.of(2031, 1, 1)  | LocalDate.of(2031, 1, 8)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2041, 7, 14)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2031, 7, 22)
    }

    def "Should not throw exception when dates range is not exceeded"() {
        given:
        def formDate = startDate
        def toDate = endDate

        when:
        validator.validateDates(startDate, endDate)

        then:
        notThrown(InvalidCaseRequestDatesRangeException)

        where:
        startDate                 | endDate
        LocalDate.of(2031, 1, 1)  | LocalDate.of(2031, 1, 7)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2031, 7, 15)
        LocalDate.of(2031, 7, 15) | LocalDate.of(2031, 7, 20)
    }
}
