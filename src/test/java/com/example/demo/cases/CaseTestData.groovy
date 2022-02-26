package com.example.demo.cases

import com.example.demo.cases.enums.CaseCategory
import com.example.demo.cases.request.CaseRequest

import java.time.LocalDate

class CaseTestData {

    public static def caseRequest = new CaseRequest(
            CaseCategory.TRAININGS,
            'new_case_comment',
            'new_case_content',
            LocalDate.of(2031, 8, 8)
    )
}
