package com.project.saas_billing_system.controller.v1.subscription.subscription;

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

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionPlanService planService;

    @GetMapping("/plans")
    public ResponseEntity<ApiResponse<List<SubscriptionPlan>>> listPlans() {
        return ResponseEntity.ok(ApiResponse.success(planService.getActivePlans()));
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
