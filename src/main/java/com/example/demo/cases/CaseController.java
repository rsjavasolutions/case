package com.example.demo.cases;

import com.example.demo.cases.request.CaseRequest;
import com.example.demo.cases.response.CaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cases")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private Map<LocalDate, List<CaseResponse>> getCasesByParameters(@RequestParam(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                    @RequestParam(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return caseService.getCasesInDatesRange(fromDate, toDate);
    }

    @GetMapping("{caseUuid}")
    @ResponseStatus(HttpStatus.OK)
    private CaseResponse getCaseByUuid(@PathVariable String caseUuid) {
        return caseService.getCaseByUuid(caseUuid);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCase(@RequestBody @Valid CaseRequest request) {
        return caseService.saveCase(request);
    }

    @DeleteMapping("{caseUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteCase(@PathVariable String caseUuid) {
        caseService.deleteCaseByUuid(caseUuid);
    }
}
