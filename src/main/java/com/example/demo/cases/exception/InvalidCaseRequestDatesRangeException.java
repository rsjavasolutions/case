package com.example.demo.cases.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCaseRequestDatesRangeException extends RuntimeException {

    private final static String MESSAGE = "Range between dates is too large: %s, maximum is %s";

    public InvalidCaseRequestDatesRangeException(long rangeBetweenDates, int maxRange) {
        super(String.format(MESSAGE, rangeBetweenDates, maxRange));
    }
}