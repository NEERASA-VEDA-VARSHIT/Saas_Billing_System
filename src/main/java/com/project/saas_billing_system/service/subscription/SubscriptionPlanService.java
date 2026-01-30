package com.project.saas_billing_system.service.subscription;

import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.subscription.SubscriptionPlan;
import com.project.saas_billing_system.repository.subscription.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "subscriptionPlans", key = "#id")
    public SubscriptionPlan getById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubscriptionPlan", "id", id));
    }

    @Transactional(readOnly = true)
    public List<SubscriptionPlan> getActivePlans() {
        return planRepository.findByActiveTrue();
    }
}
