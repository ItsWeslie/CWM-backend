package com.cwm.dto;

import jakarta.validation.constraints.*;

public record MemberRequest(
        @NotBlank(message = "Name must not be empty")
        @Size(max = 50)
        String name,

        @NotBlank(message = "Designation must not be empty")
        @Size(min = 2, max = 25, message = "Designation must be between 2 and 100 characters")
        String designation,

        @NotBlank(message = "Phone must not be empty")
        @Pattern(regexp = "^\\d{10}$", message = "Phone must be exactly 10 digits")
        String phone
) {}