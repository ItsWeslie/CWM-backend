package com.cwm.dto.event;

import com.cwm.enums.Category;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EventRequest(

    @FutureOrPresent(message = "Date must be future or present")
    @NotNull(message = "Date must required")
    LocalDate date,

    @NotBlank(message = "Category must not be empty")
    String category,

    @Size(max = 100,message = "Title must be with 100 characters")
    @NotBlank(message = "Title must not be empty")
    String title,

    @NotBlank(message = "Description must not be empty")
    @Size(max = 255,message = "Description size must be 255 characters")
    String description
    ){}
