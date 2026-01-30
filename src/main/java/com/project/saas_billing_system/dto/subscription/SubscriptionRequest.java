package com.project.saas_billing_system.dto.subscription;

import com.project.saas_billing_system.model.subscription.BillingCycle;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequest {

    @NotNull(message = "Organization ID is required")
    private Long organizationId;

    @NotNull(message = "Plan ID is required")
    private Long planId;

    private BillingCycle billingCycle;
}
