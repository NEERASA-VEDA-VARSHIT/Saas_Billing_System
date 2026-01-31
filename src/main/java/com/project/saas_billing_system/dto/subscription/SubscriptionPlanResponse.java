package com.project.saas_billing_system.dto.subscription;

import com.project.saas_billing_system.model.subscription.BillingCycle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Subscription plan response")
public class SubscriptionPlanResponse {

    @Schema(description = "Plan ID")
    private Long id;

    @Schema(description = "Plan name")
    private String name;

    @Schema(description = "Plan description")
    private String description;

    @Schema(description = "Price")
    private BigDecimal price;

    @Schema(description = "Billing cycle")
    private BillingCycle billingCycle;

    @Schema(description = "Trial days")
    private Integer trialDays;

    @Schema(description = "Whether the plan is active")
    private boolean active;
}
