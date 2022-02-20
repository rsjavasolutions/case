package com.example.demo.cases.validator;

import com.example.demo.cases.exception.InvalidCaseRequestDatesException;
import com.example.demo.cases.exception.InvalidCaseRequestDatesRangeException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CaseRequestDatesRangeValidator {

    //maksymalna rozpiętość dni dla których można aktualnie zaprezentować sprawy w kalendarzu
// jeżeli użytkownik będzie chciał zobaczyć w jednm momencie sprwy dla 8 i więcej dni dostanie wyjątek
    private static final int MAXIMUM_DATE_RANGE = 7;

    public static void validateDates(LocalDate fromDate, LocalDate toDate) {
        if (toDate.isBefore(fromDate)) {
            throw new InvalidCaseRequestDatesException(toDate, fromDate);
        }
        long daysBetweenDates = ChronoUnit.DAYS.between(fromDate, toDate);
        if (daysBetweenDates + 1 > MAXIMUM_DATE_RANGE) {
            throw new InvalidCaseRequestDatesRangeException(daysBetweenDates, MAXIMUM_DATE_RANGE);
        }
    }
}