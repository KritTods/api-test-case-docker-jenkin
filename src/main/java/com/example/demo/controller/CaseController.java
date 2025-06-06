package com.example.demo.controller;

import com.example.demo.dto.CancelCaseRequest;
import com.example.demo.dto.CancelCaseResponse;
import com.example.demo.dto.request.CaseStatusRequest;
import com.example.demo.dto.response.CaseStatusResponse;
import com.example.demo.model.ConfirmCaseResponse;
import com.example.demo.service.CancelCaseService;
import com.example.demo.service.CaseStatusService;
import com.example.demo.service.CaseTimelineService;
import com.example.demo.service.CompleteCaseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/case")
public class CaseController {

    private final CompleteCaseService completeCaseService;
    private final CancelCaseService cancelCaseService;
    private final CaseStatusService caseStatusService;
    private final CaseTimelineService caseTimelineService;


    @Autowired
    public CaseController(CompleteCaseService completeCaseService,
                          CancelCaseService cancelCaseService,
                          CaseStatusService caseStatusService,
                          CaseTimelineService caseTimelineService) {
        this.completeCaseService = completeCaseService;
        this.cancelCaseService = cancelCaseService;
        this.caseStatusService = caseStatusService;
        this.caseTimelineService = caseTimelineService;
    }

    @Operation(summary = "API UpdateCaseStatus Complete")
    @PostMapping("/complete")
    public ConfirmCaseResponse processCase(
            @RequestParam String encryptedData,
            @RequestParam String initialVector
    ) {
        return completeCaseService.processRequest(encryptedData, initialVector);
    }

    @Operation(summary = "API UpdateCaseStatus Cancel")
    @PostMapping("/cancel")
    public ResponseEntity<CancelCaseResponse> cancelCase(@RequestBody CancelCaseRequest request) {
        CancelCaseResponse response = cancelCaseService.cancelCase(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "API UpdateCaseStatus resolveCase")
    @PostMapping("/resolve")
    public ResponseEntity<CaseStatusResponse> resolveCase(@RequestBody CaseStatusRequest request) {
        CaseStatusResponse response = caseStatusService.resolveCase(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "API RetriveCase Timeline")
    @PostMapping("/retrieve")
    public ResponseEntity<CaseStatusResponse> retrieve(@RequestBody CaseStatusRequest request) {
        return ResponseEntity.ok(caseTimelineService.retrieveCaseTimeline(request));
    }
}
