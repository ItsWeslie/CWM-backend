package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.subscription.SubscriptionRequest;
import com.cwm.dto.subscription.SubscriptionResponse;
import com.cwm.exception.SubscriptionNotFoundException;
import com.cwm.exception.UserNotFoundException;
import com.cwm.mapper.SubscriptionMapper;
import com.cwm.model.Member;
import com.cwm.model.Subscription;
import com.cwm.repository.MemberRepo;
import com.cwm.repository.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSubscriptionServiceImpl implements AdminSubscriptionService {

    private final MemberRepo memberRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PageResponse<SubscriptionResponse>> getAllSubscriptions(Pageable pageable) {

        Page<Subscription> subscriptions = subscriptionRepo.findAll(pageable);
        Page<SubscriptionResponse> subscriptionResponses = subscriptions.map(SubscriptionMapper::toResponse);

        PageResponse<SubscriptionResponse> response = PageResponse.<SubscriptionResponse>builder()
                .content(subscriptionResponses.getContent())
                .size(subscriptionResponses.getSize())
                .page(subscriptionResponses.getNumber())
                .totalElements(subscriptionResponses.getTotalElements())
                .totalPages(subscriptionResponses.getTotalPages())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> addSubscription(SubscriptionRequest subscriptionRequest) {
        Member member = memberRepo.findById(subscriptionRequest.memberId())
                .orElseThrow(()-> new UserNotFoundException("Member not found with id: " +
                        subscriptionRequest.memberId()));
        Subscription newSubscription = subscriptionMapper.apply(member, subscriptionRequest);
        subscriptionRepo.save(newSubscription);
        return ResponseEntity.ok(new APIResponse("Subscription added successfully"));
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> updateSubscription(long id, SubscriptionRequest subscriptionRequest) {

        Subscription existingSubscription = subscriptionRepo.findById(id)
                .orElseThrow(()->new SubscriptionNotFoundException("Subscription not found with id: "+id));

        Member member = memberRepo.findById(subscriptionRequest.memberId())
                .orElseThrow(()->new UserNotFoundException("Member not found with id: "+
                        subscriptionRequest.memberId()));

        Subscription updatedSubscription = subscriptionMapper
                .existingSubscriptionToUpdatedSubscription(
                        member,
                        existingSubscription,
                        subscriptionRequest);

        subscriptionRepo.save(updatedSubscription);

        return ResponseEntity.ok(new APIResponse("Subscription updated successfully"));
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> deleteSubscription(long id) {
        int isDeleted = subscriptionRepo.deleteByIdAndReturnCount(id);
        if (isDeleted == 0) {
            throw new SubscriptionNotFoundException("Subscription not found with id: "+id);
        }
        return ResponseEntity.ok(new APIResponse("Subscription deleted successfully"));
    }
}
