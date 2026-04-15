package com.cwm.strategy.report;

import com.cwm.dto.report.ReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportFilterStrategyFactory {

    private final List<ReportFilterStrategy> strategies;

    public ReportFilterStrategy getStrategy(ReportRequest request) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(request))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No filter strategy found for given request. " +
                                "Provide fromDate+toDate, month+year, or year."
                ));
    }
}