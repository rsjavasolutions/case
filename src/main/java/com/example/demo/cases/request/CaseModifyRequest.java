package com.example.demo.cases.request;

import com.example.demo.cases.enums.CaseCategory;
import com.example.demo.cases.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CaseModifyRequest {
    @NotNull
    private CaseCategory category;
    private String comment;
    private String content;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate contractualVerificationDate;
    @NotNull
    private Status status;
}
