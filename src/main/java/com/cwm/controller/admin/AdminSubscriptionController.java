package com.cwm.controller.admin;

import com.cwm.api.APIConstants;
import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.subscription.SubscriptionRequest;
import com.cwm.dto.subscription.SubscriptionResponse;
import com.cwm.service.adminService.AdminSubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIConstants.Admin.SUBSCRIPTION)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSubscriptionController {

    private final AdminSubscriptionService adminSubscriptionService;

    @GetMapping
    public ResponseEntity<PageResponse<SubscriptionResponse>> getAllSubscriptions(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10")int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return adminSubscriptionService.getAllSubscriptions(pageable);
    }

    @PostMapping
    public ResponseEntity<APIResponse> addSubscription(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return adminSubscriptionService.addSubscription(subscriptionRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse> updateSubscription(@PathVariable long id,
                                                          @Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return adminSubscriptionService.updateSubscription(id,subscriptionRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteSubscription(@PathVariable long id) {
        return adminSubscriptionService.deleteSubscription(id);
    }
}
