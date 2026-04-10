package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.event.EventRequest;
import com.cwm.dto.event.EventResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminEventService {

    ResponseEntity<APIResponse> createEvent(EventRequest eventRequest);

    ResponseEntity<PageResponse<EventResponse>> getAllEvents(Pageable pageable);

    ResponseEntity<APIResponse> deleteEvent(long id);

    ResponseEntity<APIResponse> updateEvent(long id,EventRequest eventRequest);
}
