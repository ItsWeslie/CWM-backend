package com.cwm.mapper;

import com.cwm.dto.subscription.SubscriptionRequest;
import com.cwm.dto.subscription.SubscriptionResponse;
import com.cwm.enums.PaymentStatus;
import com.cwm.model.Member;
import com.cwm.model.Subscription;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class SubscriptionMapper {

    public Subscription apply(Member member, SubscriptionRequest subscriptionRequest) {
        return Subscription.builder()
                .date(subscriptionRequest.date())
                .month(subscriptionRequest.date().getMonth())
                .year(Year.of(subscriptionRequest.date().getYear()))
                .amount(subscriptionRequest.amount())
                .paymentStatus(PaymentStatus.valueOf(subscriptionRequest.paymentStatus().toUpperCase()))
                .member(member)
                .build();
    }

    public static SubscriptionResponse toResponse(Subscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .date(subscription.getDate())
                .month(subscription.getMonth())
                .year(subscription.getYear())
                .amount(subscription.getAmount())
                .paymentStatus(subscription.getPaymentStatus())
                .memberId(subscription.getMember().getMemberId())
                .build();
    }

    public Subscription existingSubscriptionToUpdatedSubscription(Member member,
                                                                  Subscription existingsubscription,
                                                                  SubscriptionRequest subscriptionRequest) {
        existingsubscription.setAmount(subscriptionRequest.amount());
        existingsubscription.setPaymentStatus(PaymentStatus
                .valueOf(subscriptionRequest
                .paymentStatus()
                .toUpperCase()
                ));
        existingsubscription.setDate(subscriptionRequest.date());
        existingsubscription.setMonth(subscriptionRequest.date().getMonth());
        existingsubscription.setYear(Year.of(subscriptionRequest.date().getYear()));
        existingsubscription.setMember(member);
        return existingsubscription;
    }
}
