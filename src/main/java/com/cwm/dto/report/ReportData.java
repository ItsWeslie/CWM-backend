package com.cwm.dto.report;

import com.cwm.model.Payment;
import com.cwm.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportData{

        private List<Payment> payments;
        private List<Subscription> subscriptions;
        private long totalPayments;
        private long totalSubscriptions;
        private BigDecimal totalPaymentAmount;
        private BigDecimal totalSubscriptionAmount;
        private String reportTitle;

    public BigDecimal grandTotal() {
        return totalPaymentAmount.add(totalSubscriptionAmount);
    }
}