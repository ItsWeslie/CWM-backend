package com.cwm.strategy.report;

import com.cwm.dto.report.ReportData;
import com.cwm.dto.report.ReportRequest;
import com.cwm.model.Payment;
import com.cwm.model.Subscription;
import com.cwm.repository.PaymentRepo;
import com.cwm.repository.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DateRangeFilterStrategy implements ReportFilterStrategy {

    private final PaymentRepo paymentRepository;
    private final SubscriptionRepo subscriptionRepository;

    @Override
    public boolean supports(ReportRequest request) {
        return request.hasDateRange();
    }

    @Override
    public ReportData fetchData(ReportRequest request) {

        List<Payment> payments = paymentRepository
                .findByDateBetween(
                        request.getFromDate(),
                        request.getToDate()
                );

        List<Subscription> subscriptions = subscriptionRepository
                .findByDateBetween(
                        request.getFromDate(),
                        request.getToDate()
                );

        return ReportDataBuilder.build(
                payments,
                subscriptions,
                "Report — " + request.getFromDate() + " to " + request.getToDate()
        );
    }
}