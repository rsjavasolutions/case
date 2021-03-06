package com.example.demo.cases

import com.example.demo.cases.enums.CaseCategory
import com.example.demo.cases.enums.CaseGenus
import com.example.demo.cases.enums.Status
import com.example.demo.cases.response.CaseResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CaseControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    CaseRepository caseRepository


    def "Should get all cases and return '200'status"() {
        when:
        def result = mockMvc.perform(get("/cases")
                .param("fromDate", "2031-07-01")
                .param("toDate", "2031-07-07"))

        then:
        result.andExpect(status().is(200))
    }

    def "Should throw exception when dates range is exceeded and return '400'status"() {
        when:
        def result = mockMvc.perform(get("/cases")
                .param("fromDate", "2031-07-01")
                .param("toDate", "2031-07-15"))

        then:
        result.andExpect(status().is(400))
        result.andReturn().getResolvedException().getMessage() == 'Range between dates is too large: 14, maximum is 7'
    }

    def "Should get case by uuid and return '200'status"() {
        when:
        def result = mockMvc.perform(get("/cases/c61c2ce8-30c5-4e59-acf7-caf576f286bb"))

        then:
        caseRepository.findAll().size() == 3
        result.andExpect(status().is(200))
        def caseResponse = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), CaseResponse.class)
        caseResponse.uuid == 'c61c2ce8-30c5-4e59-acf7-caf576f286bb'
        caseResponse.caseGenus == CaseGenus.MANUAL
        caseResponse.category == CaseCategory.EVENTS
        caseResponse.comment == 'some_comment_1'
        caseResponse.content == 'some_content_1'
        caseResponse.status == Status.UNREALIZED
        caseResponse.contractualVerificationDate == LocalDate.of(2031, 6, 1)
        caseResponse.realizationDate == null
    }

    def "Should throw exception when case uuid doesn't exist and return '404'status"() {
        when:
        def result = mockMvc.perform(get("/cases/aaac2ce8-30c5-4e59-acf7-caf576f28ccc"))

        then:
        result.andExpect(status().is(404))
        result.andReturn().getResolvedException().getMessage() == 'Can\'t find case with uuid: aaac2ce8-30c5-4e59-acf7-caf576f28ccc'
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    def "Should add new case and return '201'status"() {
        when:
        def result = mockMvc.perform(post("/cases")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(CaseTestData.caseRequest)))

        then:
        caseRepository.findAll().size() == 4
        result.andExpect(status().is(201))

        def newCaseUuid = result.andReturn().getResponse().getContentAsString()
        newCaseUuid != null
        newCaseUuid.size() == 36

        def optionalOfCaseEntity = caseRepository.findByUuid(newCaseUuid)
        optionalOfCaseEntity.isPresent()

        def caseEntity = optionalOfCaseEntity.get()
        caseEntity.id == 1
        caseEntity.visible
        caseEntity.caseGenus == CaseGenus.MANUAL
        caseEntity.category == CaseCategory.TRAININGS
        caseEntity.comment == 'new_case_comment'
        caseEntity.content == 'new_case_content'
        caseEntity.status == Status.UNREALIZED
        caseEntity.contractualVerificationDate == LocalDate.of(2031, 8, 8)
        caseEntity.getRealizationDate() == null
        caseEntity.creationDateTime != null
    }

    def "Should delete case by uuid"() {
        when:
        mockMvc.perform(delete("/cases/c61c2ce8-30c5-4e59-acf7-caf576f286bb"))

        then:
        caseRepository.findAll().size() == 2
        caseRepository.findByUuid('c61c2ce8-30c5-4e59-acf7-caf576f286bb').isEmpty()
    }

    def "Should update case with status UNREALIZED and return '200'status"() {
        when:
        mockMvc.perform(put("/cases/u61c2ce8-30c5-4e59-acf7-caf576f286aa")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(CaseTestData.caseModifyRequestWithStatusUnrealized)))

        then:
        def optionalOfCaseEntity = caseRepository.findByUuid('u61c2ce8-30c5-4e59-acf7-caf576f286aa')
        optionalOfCaseEntity.isPresent()

        def caseEntity = optionalOfCaseEntity.get()
        caseEntity.id == -2
        caseEntity.visible
        caseEntity.caseGenus == CaseGenus.MANUAL
        caseEntity.category == CaseCategory.MEETINGS
        caseEntity.comment == 'updated_case_comment'
        caseEntity.content == 'updated_case_content'
        caseEntity.status == Status.UNREALIZED
        caseEntity.contractualVerificationDate == LocalDate.of(2055, 8, 8)
        caseEntity.getRealizationDate() == null
        caseEntity.creationDateTime != null
    }

    def "Should update case with by status REALIZED and return '200'status"() {
        when:
        def result = mockMvc.perform(put("/cases/{caseUuid}", 'u61c2ce8-30c5-4e59-acf7-caf576f286aa')
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(CaseTestData.caseModifyRequestWithStatusRealized)))

        then:
        def optionalOfCaseEntity = caseRepository.findByUuid('u61c2ce8-30c5-4e59-acf7-caf576f286aa')
        optionalOfCaseEntity.isPresent()

        def caseEntity = optionalOfCaseEntity.get()
        caseEntity.id == -2
        caseEntity.visible
        caseEntity.caseGenus == CaseGenus.MANUAL
        caseEntity.category == CaseCategory.EVENTS
        caseEntity.comment == 'updated_case_comment'
        caseEntity.content == 'updated_case_content'
        caseEntity.status == Status.REALIZED
        caseEntity.contractualVerificationDate == LocalDate.of(2044, 8, 8)
        caseEntity.getRealizationDate() != null
        caseEntity.creationDateTime != null
    }

    def "Should throw exception when case Status is REALIZED and return '422'status"() {
        when:
        def result = mockMvc.perform(put("/cases/123c2ce8-30c5-4e59-acf7-caf576f286aa")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(CaseTestData.caseModifyRequestWithStatusUnrealized)))

        then:
        result.andReturn().getResolvedException().getMessage() == ('Can\'t update case with status REALIZED, case uuid: 123c2ce8-30c5-4e59-acf7-caf576f286aa')
    }
}