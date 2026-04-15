package com.cwm.service.adminService.report;

import com.cwm.dto.report.ReportRequest;
import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<byte[]> generateReport(ReportRequest request);
}