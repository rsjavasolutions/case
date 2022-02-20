package com.example.demo.cases.request;

import com.example.demo.cases.enums.CaseCategory;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CaseRequest implements Serializable {
    @NotNull
    private CaseCategory category;
    private String comment;
    private String content;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate contractualVerificationDate;
}
