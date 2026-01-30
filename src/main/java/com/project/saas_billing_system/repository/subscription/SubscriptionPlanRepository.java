package com.project.saas_billing_system.repository.subscription;

import com.project.saas_billing_system.model.subscription.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    List<SubscriptionPlan> findByActiveTrue();
}
