package com.example.demo.cases

import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import com.example.demo.cases.enums.CaseCategory
import com.example.demo.cases.enums.CaseGenus
import com.example.demo.cases.enums.Status
import com.example.demo.cases.exception.CaseNotFoundException
import com.example.demo.cases.request.CaseRequest
import org.spockframework.spring.SpringBean
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class CaseServiceTest extends Specification {

    private static final LocalDate FROM_DATE = LocalDate.of(2031, 7, 14)
    private static final LocalDate TO_DATE = LocalDate.of(2031, 7, 19)
    private static final String CASE_UUID = '489ad7ee-2dce-4d72-b5fd-ca5a83d4ae4b'

    @SpringBean
    CaseRepository caseRepository = Mock()

    @Subject
    CaseService caseService;

    def "Should get cases in dates"() {
        given:
        def cases = Set.of(
                new CaseEntity(CaseGenus.MANUAL, CaseCategory.EVENTS, 'no_comment', 'some_content', Status.UNREALIZED, LocalDate.of(2031, 7, 15)),
                new CaseEntity(CaseGenus.MANUAL, CaseCategory.MEETINGS, 'xyz', 'new_content', Status.UNREALIZED, LocalDate.of(2031, 7, 15)),
                new CaseEntity(CaseGenus.MANUAL, CaseCategory.TRAININGS, 'train_hard', 'gym_training', Status.UNREALIZED, LocalDate.of(2031, 7, 18)),
        )

        and:
        caseRepository.findAllByContractualVerificationDateBetween(FROM_DATE, TO_DATE) >> cases

        when:
        def dateToCase = caseService.getCasesInDatesRange(FROM_DATE, TO_DATE)

        then:
        dateToCase.size() == 2
        dateToCase.get(LocalDate.of(2031, 7, 18)).size() == 1
        dateToCase.get(LocalDate.of(2031, 7, 15)).size() == 2
    }

    def "Should get case by uuid"() {
        given:
        def mockedCase = Optional.of(
                new CaseEntity(CaseGenus.MANUAL, CaseCategory.TRAININGS, 'train_hard', 'gym_training', Status.UNREALIZED, LocalDate.of(2031, 7, 18)))

        and:
        caseRepository.findByUuid(CASE_UUID) >> mockedCase

        when:
        def caseByUuid = caseService.getCaseByUuid(CASE_UUID)

        then:
        notThrown(CaseNotFoundException)
        caseByUuid != null
        caseByUuid.getCaseGenus() == CaseGenus.MANUAL
        caseByUuid.getCategory() == CaseCategory.TRAININGS
        caseByUuid.getRealizationDate() == null
    }

    def "Should save case"() {
        given:
        def caseRequest = new CaseRequest(CaseCategory.TRAININGS, 'train_hard', 'gym_training', LocalDate.of(2031, 7, 18))

        when:
        def uuid = caseService.saveCase(caseRequest)

        then:
        uuid != null
        uuid.size() == 36
    }

    def "Should delete case"() {
        when:
        caseService.deleteCaseByUuid(CASE_UUID)

        then:
        1 * caseRepository.deleteByUuid(_)
    }
}
