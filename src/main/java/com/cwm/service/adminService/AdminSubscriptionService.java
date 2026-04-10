package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.subscription.SubscriptionRequest;
import com.cwm.dto.subscription.SubscriptionResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminSubscriptionService {
    ResponseEntity<PageResponse<SubscriptionResponse>> getAllSubscriptions(Pageable pageable);

    ResponseEntity<APIResponse> addSubscription(SubscriptionRequest subscriptionRequest);

    ResponseEntity<APIResponse> updateSubscription(long id, SubscriptionRequest subscriptionRequest);

    ResponseEntity<APIResponse> deleteSubscription(long id);
}
