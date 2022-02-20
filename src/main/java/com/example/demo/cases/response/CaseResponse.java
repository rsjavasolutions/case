package com.example.demo.cases.response;

import com.example.demo.cases.enums.CaseCategory;
import com.example.demo.cases.enums.CaseGenus;
import com.example.demo.cases.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CaseResponse {
    private final String uuid;
    private final CaseGenus caseGenus;
    private final CaseCategory category;
    private final String comment;
    private final String content;
    private final Status status;
    private final LocalDate contractualVerificationDate;
    private final LocalDate realizationDate;
    private final LocalDateTime creationDateTime;
}
