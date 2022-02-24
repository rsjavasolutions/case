package com.example.demo.cases.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCaseRequestDatesException extends RuntimeException {

    private final static String MESSAGE = "Case request params to date %s can not be before from date: %s";

    public InvalidCaseRequestDatesException(LocalDate reminderToDate, LocalDate reminderFromDate) {
        super(String.format(MESSAGE, reminderToDate, reminderFromDate));
    }
}
