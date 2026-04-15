package com.cwm.controller.admin;

import com.cwm.api.APIConstants;
import com.cwm.dto.report.ReportRequest;
import com.cwm.service.adminService.report.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(APIConstants.Admin.REPORTS)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {

    private final ReportService reportService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport(@Valid @RequestBody ReportRequest request) {
        log.info("Report download requested: {}", request);
        return reportService.generateReport(request);
    }
}