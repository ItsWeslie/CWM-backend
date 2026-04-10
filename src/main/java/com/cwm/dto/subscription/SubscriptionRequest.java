package com.cwm.dto.subscription;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionRequest(
        @Positive(message = "Amount must be greater than zero")
        @NotNull(message = "Amount is required")
        @Digits(integer = 8,
                fraction = 2,
                message = "Amount must have max 8 integer and 2 decimal digits")
        BigDecimal amount,
        @NotNull(message = "Date is required")
        @PastOrPresent(message = "Date must be past or present date")
        LocalDate date,
        @NotNull(message = "Member id is required")
        Long memberId,
        @NotBlank(message = "Payment status is required")
        String paymentStatus
)
{}
