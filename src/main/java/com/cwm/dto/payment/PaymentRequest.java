package com.cwm.dto.payment;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequest(

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotBlank(message = "Paid_to is required")
        @Size(max = 50,message = "Paid_to must be within 50 characters")
        String paidTo,

        @Positive
        @NotNull(message = "Amount is required")
        @Digits(
                integer = 6,
                fraction = 2,
                message = "Amount must have max 8 integer and 2 decimal digits")
        BigDecimal amount,

        @NotBlank(message = "Description is required")
        String description,

        MultipartFile paymentDocument
) {}
