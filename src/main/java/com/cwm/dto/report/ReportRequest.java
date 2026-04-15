package com.cwm.dto.report;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.time.Month;

@Data
@AllArgsConstructor
@Builder
public class ReportRequest {

        @PastOrPresent(message = "From date cannot be a future date")
        LocalDate fromDate;

        @PastOrPresent(message = "To date cannot be a future date")
        LocalDate toDate;

        Month month;

        @Min(value = 2000, message = "Year must be 2000 or later")
        @Max(value = 2100, message = "Year must be 2100 or earlier")
        Integer year;


    public ReportRequest() {
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException(
                    "fromDate must not be after toDate"
            );
        }
        if (month != null && year == null) {
            throw new IllegalArgumentException(
                    "Year is required when month is provided"
            );
        }
        if (fromDate == null && toDate == null && month == null && year == null) {
            throw new IllegalArgumentException(
                    "At least one filter must be provided"
            );
        }
    }

    public boolean hasDateRange() {
        return fromDate != null && toDate != null;
    }

    public boolean hasMonth() {
        return month != null && year != null;
    }

    public boolean hasYearOnly() {
        return year != null && month == null
                && fromDate == null && toDate == null;
    }
}