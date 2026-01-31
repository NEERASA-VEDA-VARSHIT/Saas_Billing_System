package com.project.saas_billing_system.config.data;

import com.project.saas_billing_system.model.subscription.BillingCycle;
import com.project.saas_billing_system.model.subscription.SubscriptionPlan;
import com.project.saas_billing_system.repository.subscription.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class SubscriptionPlanDataLoader implements ApplicationRunner {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (subscriptionPlanRepository.count() > 0) {
            return;
        }
        log.info("Seeding subscription plans (Starter, Pro, Enterprise)");
        List<SubscriptionPlan> plans = List.of(
                plan("Starter", "Basic plan", new BigDecimal("29.99"), BillingCycle.MONTHLY, 14),
                plan("Pro", "Professional plan", new BigDecimal("99.99"), BillingCycle.MONTHLY, 7),
                plan("Enterprise", "Enterprise plan", new BigDecimal("299.99"), BillingCycle.ANNUAL, 0)
        );
        subscriptionPlanRepository.saveAll(plans);
    }

    private static SubscriptionPlan plan(String name, String description, BigDecimal price, BillingCycle cycle, int trialDays) {
        SubscriptionPlan p = new SubscriptionPlan();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setBillingCycle(cycle);
        p.setTrialDays(trialDays);
        p.setActive(true);
        return p;
    }
}
