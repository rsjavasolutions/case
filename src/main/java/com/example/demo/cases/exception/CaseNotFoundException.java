package com.example.demo.cases.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CaseNotFoundException extends RuntimeException {

    private final static String MESSAGE = "Can't find case with uuid: %s";

    public CaseNotFoundException(String caseUuid) {
        super(String.format(MESSAGE, caseUuid));
    }
}
