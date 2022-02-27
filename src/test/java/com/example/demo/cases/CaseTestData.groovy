package com.example.demo.cases

import com.example.demo.cases.enums.CaseCategory
import com.example.demo.cases.enums.Status
import com.example.demo.cases.request.CaseModifyRequest
import com.example.demo.cases.request.CaseRequest

import java.time.LocalDate

class CaseTestData {

    public static def caseRequest = new CaseRequest(
            CaseCategory.TRAININGS,
            'new_case_comment',
            'new_case_content',
            LocalDate.of(2031, 8, 8)
    )

    public static def caseModifyRequestWithStatusRealized = new CaseModifyRequest(
            CaseCategory.EVENTS,
            'updated_case_comment',
            'updated_case_content',
            LocalDate.of(2044, 8, 8),
            Status.REALIZED
    )

    public static def caseModifyRequestWithStatusUnrealized = new CaseModifyRequest(
            CaseCategory.MEETINGS,
            'updated_case_comment',
            'updated_case_content',
            LocalDate.of(2055, 8, 8),
            Status.UNREALIZED
    )
}
