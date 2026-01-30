package com.project.saas_billing_system.service.subscription;

import com.project.saas_billing_system.dto.subscription.SubscriptionRequest;
import com.project.saas_billing_system.dto.subscription.SubscriptionResponse;
import com.project.saas_billing_system.event.SubscriptionEvent;
import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.subscription.Subscription;
import com.project.saas_billing_system.model.subscription.SubscriptionStatus;
import com.project.saas_billing_system.producer.EventProducer;
import com.project.saas_billing_system.repository.subscription.SubscriptionRepository;
import com.project.saas_billing_system.service.identity.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanService planService;
    private final OrganizationService organizationService;
    private final EventProducer eventProducer;

    @Transactional
    public SubscriptionResponse create(SubscriptionRequest request) {
        organizationService.getById(request.getOrganizationId());
        var plan = planService.getById(request.getPlanId());
        Subscription sub = new Subscription();
        sub.setOrganization(organizationService.getById(request.getOrganizationId()));
        sub.setPlan(plan);
        sub.setStatus(SubscriptionStatus.PENDING);
        Instant now = Instant.now();
        sub.setCurrentPeriodStart(now);
        sub.setCurrentPeriodEnd(now.plusSeconds(30L * 24 * 3600)); // 30 days
        sub = subscriptionRepository.save(sub);
        eventProducer.publishSubscriptionEvent(SubscriptionEvent.builder()
                .subscriptionId(sub.getId())
                .organizationId(sub.getOrganization().getId())
                .planId(plan.getId())
                .status(sub.getStatus())
                .currentPeriodStart(sub.getCurrentPeriodStart())
                .currentPeriodEnd(sub.getCurrentPeriodEnd())
                .build());
        return toResponse(sub);
    }

    @Transactional(readOnly = true)
    public SubscriptionResponse getById(Long id) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        return toResponse(sub);
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getByOrganizationId(Long organizationId) {
        return subscriptionRepository.findByOrganizationId(organizationId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubscriptionResponse cancel(Long id) {
        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", id));
        sub.setStatus(SubscriptionStatus.CANCELLED);
        sub.setCancelledAt(Instant.now());
        sub = subscriptionRepository.save(sub);
        eventProducer.publishSubscriptionEvent(SubscriptionEvent.builder()
                .subscriptionId(sub.getId())
                .organizationId(sub.getOrganization().getId())
                .planId(sub.getPlan().getId())
                .status(sub.getStatus())
                .build());
        return toResponse(sub);
    }

    private SubscriptionResponse toResponse(Subscription sub) {
        SubscriptionResponse r = new SubscriptionResponse();
        r.setId(sub.getId());
        r.setOrganizationId(sub.getOrganization().getId());
        r.setPlanId(sub.getPlan().getId());
        r.setPlanName(sub.getPlan().getName());
        r.setStatus(sub.getStatus());
        r.setCurrentPeriodStart(sub.getCurrentPeriodStart());
        r.setCurrentPeriodEnd(sub.getCurrentPeriodEnd());
        r.setTrialEnd(sub.getTrialEnd());
        r.setCancelledAt(sub.getCancelledAt());
        return r;
    }
}
