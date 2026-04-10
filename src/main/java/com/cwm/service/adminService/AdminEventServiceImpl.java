package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.event.EventRequest;
import com.cwm.dto.event.EventResponse;
import com.cwm.exception.EventNotFoundException;
import com.cwm.mapper.EventMapper;
import com.cwm.model.Event;
import com.cwm.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventMapper eventMapper;
    private final EventRepo eventRepo;

    @Override
    @Transactional
    public ResponseEntity<APIResponse> createEvent(EventRequest eventRequest) {
        Event newEvent = eventMapper.apply(eventRequest);
        eventRepo.save(newEvent);
        return ResponseEntity.ok(new APIResponse("Event created successfully"));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PageResponse<EventResponse>> getAllEvents(Pageable pageable) {

        Page<Event> events = eventRepo.findAll(pageable);
        Page<EventResponse> responsePage = events.map(EventMapper::toResponse);

        PageResponse<EventResponse> eventResponsePage = PageResponse.<EventResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .build();

        return ResponseEntity.ok(eventResponsePage);
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> deleteEvent(long id) {
        int isDeleted = eventRepo.deleteByIdAndReturnCount(id);
        if(isDeleted==0)
            throw new EventNotFoundException("Event not found with id " + id);
        return ResponseEntity.ok(new APIResponse("Event deleted successfully"));
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> updateEvent(long id, EventRequest eventRequest) {
        Event existingEvent = eventRepo.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
        Event updatedEvent = eventMapper.existingEventToUpdatedEvent(existingEvent, eventRequest);
        eventRepo.save(updatedEvent);
        return ResponseEntity.ok(new APIResponse("Event updated successfully"));
    }
}
