package com.project.saas_billing_system.event;

import com.project.saas_billing_system.model.subscription.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEvent {

    private Long subscriptionId;
    private Long organizationId;
    private Long planId;
    private SubscriptionStatus status;
    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;
}
