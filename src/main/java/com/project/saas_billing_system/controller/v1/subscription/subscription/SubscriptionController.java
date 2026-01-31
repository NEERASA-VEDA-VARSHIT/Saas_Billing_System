package com.project.saas_billing_system.controller.v1.subscription.subscription;

import com.project.saas_billing_system.dto.subscription.SubscriptionPlanResponse;
import com.project.saas_billing_system.dto.subscription.SubscriptionRequest;
import com.project.saas_billing_system.dto.subscription.SubscriptionResponse;
import com.project.saas_billing_system.model.subscription.SubscriptionPlan;
import com.project.saas_billing_system.service.subscription.SubscriptionPlanService;
import com.project.saas_billing_system.service.subscription.SubscriptionService;
import com.project.saas_billing_system.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionPlanService planService;

    @GetMapping("/plans")
    public ResponseEntity<ApiResponse<List<SubscriptionPlanResponse>>> listPlans() {
        List<SubscriptionPlanResponse> list = planService.getActivePlans().stream()
                .map(this::toPlanResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    private SubscriptionPlanResponse toPlanResponse(SubscriptionPlan plan) {
        return SubscriptionPlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .price(plan.getPrice())
                .billingCycle(plan.getBillingCycle())
                .trialDays(plan.getTrialDays())
                .active(plan.isActive())
                .build();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionResponse>> create(@Valid @RequestBody SubscriptionRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success(subscriptionService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.getById(id)));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<SubscriptionResponse>>> getByOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.getByOrganizationId(organizationId)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.cancel(id)));
    }
}
