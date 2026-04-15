package com.cwm.service.adminService.report;

import com.cwm.dto.report.ReportData;
import com.cwm.dto.report.ReportRequest;
import com.cwm.strategy.report.ReportFilterStrategy;
import com.cwm.strategy.report.ReportFilterStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportFilterStrategyFactory strategyFactory;
    private final ExcelGeneratorService excelGeneratorService;

    private String buildFilename(ReportRequest request) {
        if (request.hasDateRange()) {
            return String.format("report_%s_to_%s.xlsx",
                    request.getFromDate(),
                    request.getToDate());
        }
        if (request.hasMonth()) {
            return String.format("report_%s_%d.xlsx",
                    request.getMonth().name().toLowerCase(),
                    request.getYear());
        }
        return String.format("report_year_%d.xlsx",
                request.getYear());
    }

    private HttpHeaders buildDownloadHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(filename)
                        .build()
        );
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        return headers;
    }

    @Override
    public ResponseEntity<byte[]> generateReport(ReportRequest request) {

        log.info("Generating report for request: {}", request);

        ReportFilterStrategy strategy = strategyFactory.getStrategy(request);

        ReportData reportData = strategy.fetchData(request);

        log.info("Fetched {} payments and {} subscriptions",
                reportData.getTotalPayments(),
                reportData.getTotalSubscriptions());

        if (reportData.getPayments().isEmpty() &&
                reportData.getSubscriptions().isEmpty()) {
            log.warn("No data found for given filters");
            return ResponseEntity
                    .noContent()
                    .build();
        }

        byte[] excelBytes;
        try {
            excelBytes = excelGeneratorService.generateReport(reportData);
        } catch (IOException e) {
            log.error("Failed to generate excel report: {}", e.getMessage());
            throw new RuntimeException("Failed to generate report. Please try again.");
        }

        String filename = buildFilename(request);
        log.info("Report generated successfully: {}", filename);

        return ResponseEntity.ok()
                .headers(buildDownloadHeaders(filename))
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .contentLength(excelBytes.length)
                .body(excelBytes);
    }

}