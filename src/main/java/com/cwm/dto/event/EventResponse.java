package com.cwm.dto.event;

import com.cwm.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponse {
    private long id;
    private LocalDate date;
    private Month month;
    private Year year;
    private Category category;
    private String title;
    private String description;
}
