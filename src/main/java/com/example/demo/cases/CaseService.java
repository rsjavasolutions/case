package com.example.demo.cases;

import com.example.demo.cases.enums.Status;
import com.example.demo.cases.exception.CaseNotFoundException;
import com.example.demo.cases.exception.InvalidStatusInCaseRequestException;
import com.example.demo.cases.mapper.CaseMapper;
import com.example.demo.cases.request.CaseModifyRequest;
import com.example.demo.cases.request.CaseRequest;
import com.example.demo.cases.response.CaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.cases.mapper.CaseMapper.mapToEntity;
import static com.example.demo.cases.validator.CaseRequestDatesRangeValidator.validateDates;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseService {

    private final CaseRepository caseRepository;

    @Transactional
    public Map<LocalDate, List<CaseResponse>> getCasesInDatesRange(LocalDate fromDate,
                                                                   LocalDate toDate) {
        validateDates(fromDate, toDate);

        return caseRepository.findAllByContractualVerificationDateBetween(fromDate, toDate)
                .stream()
                .map(CaseMapper::mapToResponse)
                .collect(Collectors.groupingBy(CaseResponse::getContractualVerificationDate));
    }

    @Transactional
    public CaseResponse getCaseByUuid(String caseUuid) {
        CaseEntity caseEntity = caseRepository.findByUuid(caseUuid).orElseThrow(() -> new CaseNotFoundException(caseUuid));

        return CaseMapper.mapToResponse(caseEntity);
    }


    @Transactional
    public String saveCase(CaseRequest request) {
        log.debug("Save case with params. CaseRequest {}", request);

        CaseEntity entity = mapToEntity(request);

        caseRepository.save(entity);

        return entity.getUuid();
    }

    @Transactional
    public void deleteCaseByUuid(String caseUuid) {
        log.debug("Delete case with uuid {}", caseUuid);

        caseRepository.deleteByUuid(caseUuid);
    }

    @Transactional
    public void updateCase(String caseUuid, CaseModifyRequest request) {
        log.debug("Update case with params. CaseRequest {}, caseUuid {}", request, caseUuid);

        CaseEntity caseEntity = caseRepository.findByUuid(caseUuid).orElseThrow(() -> new CaseNotFoundException(caseUuid));
        if (caseEntity.getStatus() == Status.REALIZED) {
            throw new InvalidStatusInCaseRequestException(caseUuid);
        }

        caseEntity.setCategory(request.getCategory());
        caseEntity.setComment(request.getComment());
        caseEntity.setContent(request.getContent());
        caseEntity.setStatus(request.getStatus());
        caseEntity.setContractualVerificationDate(request.getContractualVerificationDate());
        caseEntity.setRealizationDate(getRealizationDate(request.getStatus()));

        caseRepository.save(caseEntity);
    }

    private LocalDate getRealizationDate(Status status) {
        return status == Status.REALIZED
                ? LocalDate.now()
                : null;
    }
}

