package com.cwm.controller.admin;

import com.cwm.api.APIConstants;
import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.event.EventRequest;
import com.cwm.dto.event.EventResponse;
import com.cwm.service.adminService.AdminEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIConstants.Admin.EVENT)
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public ResponseEntity<PageResponse<EventResponse>> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return adminEventService.getAllEvents(pageable);
    }

    @PostMapping
    public ResponseEntity<APIResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        return adminEventService.createEvent(eventRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteEvent(@PathVariable long id) {
        return adminEventService.deleteEvent(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse> updateEvent(@PathVariable long id,
                                                   @Valid @RequestBody EventRequest eventRequest) {
        return adminEventService.updateEvent(id,eventRequest);
    }



}
