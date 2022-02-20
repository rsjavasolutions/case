package com.example.demo.cases.mapper;

import com.example.demo.cases.CaseEntity;
import com.example.demo.cases.enums.CaseGenus;
import com.example.demo.cases.enums.Status;
import com.example.demo.cases.request.CaseRequest;
import com.example.demo.cases.response.CaseResponse;

public class CaseMapper {

    public static CaseEntity mapToEntity(CaseRequest request) {
        return new CaseEntity(
                CaseGenus.MANUAL,
                request.getCategory(),
                request.getComment(),
                request.getContent(),
                Status.UNREALIZED,
                request.getContractualVerificationDate()
        );
    }

    public static CaseResponse mapToResponse(CaseEntity entity) {
        return new CaseResponse(
                entity.getUuid(),
                entity.getCaseGenus(),
                entity.getCategory(),
                entity.getComment(),
                entity.getContent(),
                entity.getStatus(),
                entity.getContractualVerificationDate(),
                entity.getRealizationDate(),
                entity.getCreationDateTime()
        );
    }
}
