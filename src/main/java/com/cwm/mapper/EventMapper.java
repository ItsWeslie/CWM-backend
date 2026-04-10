package com.cwm.mapper;

import com.cwm.dto.event.EventRequest;
import com.cwm.dto.event.EventResponse;
import com.cwm.enums.Category;
import com.cwm.model.Event;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.function.Function;

@Component
public class EventMapper implements Function<EventRequest, Event> {
    @Override
    public Event apply(EventRequest eventRequest) {
        return Event.builder()
                .date(eventRequest.date())
                .month(eventRequest.date().getMonth())
                .year(Year.of(eventRequest.date().getYear()))
                .category(Category.valueOf(eventRequest.category().toUpperCase()))
                .title(eventRequest.title())
                .description(eventRequest.description())
                .build();
    }

    public static EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .date(event.getDate())
                .month(event.getMonth())
                .year(event.getYear())
                .category(event.getCategory())
                .title(event.getTitle())
                .description(event.getDescription())
                .build();
    }

    public Event existingEventToUpdatedEvent(Event existingEvent,EventRequest eventRequest) {
        existingEvent.setDate(eventRequest.date());
        existingEvent.setMonth(eventRequest.date().getMonth());
        existingEvent.setYear(Year.of(eventRequest.date().getYear()));
        existingEvent.setCategory(Category.valueOf(eventRequest.category().toUpperCase()));
        existingEvent.setTitle(eventRequest.title());
        existingEvent.setDescription(eventRequest.description());
        return existingEvent;
    }
}
