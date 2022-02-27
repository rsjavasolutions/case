package com.example.demo.cases.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidStatusInCaseRequestException extends RuntimeException {

    private final static String MESSAGE = "Can't update case with status REALIZED, case uuid: %s";

    public InvalidStatusInCaseRequestException(String caseUuid) {
        super(String.format(MESSAGE, caseUuid));
    }
}
