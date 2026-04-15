package com.cwm.dto.payment;

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
public class PaymentResponse {
    private Long paymentId;
    private LocalDate date;
    private Month month;
    private Year year;
    private String paidTo;
    private BigDecimal amount;
    private String description;
    private PaymentDocumentResponse document;
}
