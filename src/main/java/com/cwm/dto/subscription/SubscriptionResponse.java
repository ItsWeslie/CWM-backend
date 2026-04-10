package com.cwm.dto.subscription;

import com.cwm.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private Month month;
    private Year year;
    private Long memberId;
    private PaymentStatus paymentStatus;
}
