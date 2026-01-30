package com.project.saas_billing_system.dto.subscription;

import com.project.saas_billing_system.model.subscription.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SubscriptionResponse {

    private Long id;
    private Long organizationId;
    private Long planId;
    private String planName;
    private SubscriptionStatus status;
    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;
    private Instant trialEnd;
    private Instant cancelledAt;
}
