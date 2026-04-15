package com.cwm.strategy.report;

import com.cwm.dto.report.ReportData;
import com.cwm.model.Payment;
import com.cwm.model.Subscription;
import java.math.BigDecimal;
import java.util.List;

public class ReportDataBuilder {

    public static ReportData build(
            List<Payment> payments,
            List<Subscription> subscriptions,
            String title) {

        BigDecimal totalPaymentAmount = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSubscriptionAmount = subscriptions.stream()
                .map(Subscription::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ReportData.builder()
                .payments(payments)
                .subscriptions(subscriptions)
                .totalPayments(payments.size())
                .totalSubscriptions(subscriptions.size())
                .totalPaymentAmount(totalPaymentAmount)
                .totalSubscriptionAmount(totalSubscriptionAmount)
                .reportTitle(title)
                .build();

    }
}