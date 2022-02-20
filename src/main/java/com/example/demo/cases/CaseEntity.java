package com.example.demo.cases;

import com.example.demo.cases.enums.CaseCategory;
import com.example.demo.cases.enums.CaseGenus;
import com.example.demo.cases.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cases")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private String uuid;
    private boolean visible = true;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private CaseGenus caseGenus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CaseCategory category;

    private String comment;

    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDate contractualVerificationDate;

    private LocalDate realizationDate;

    @Setter(AccessLevel.NONE)
    private LocalDateTime creationDateTime;

    public CaseEntity(CaseGenus caseGenus,
                      CaseCategory category,
                      String comment,
                      String content,
                      Status status,
                      LocalDate contractualVerificationDate) {
        this.uuid = UUID.randomUUID().toString();
        this.caseGenus = caseGenus;
        this.category = category;
        this.comment = comment;
        this.content = content;
        this.status = status;
        this.contractualVerificationDate = contractualVerificationDate;
        this.creationDateTime = LocalDateTime.now();
    }
}
