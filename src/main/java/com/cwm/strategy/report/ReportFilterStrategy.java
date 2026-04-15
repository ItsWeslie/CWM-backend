package com.cwm.strategy.report;

import com.cwm.dto.report.ReportData;
import com.cwm.dto.report.ReportRequest;

public interface ReportFilterStrategy {
    boolean supports(ReportRequest request);
    ReportData fetchData(ReportRequest request);
}