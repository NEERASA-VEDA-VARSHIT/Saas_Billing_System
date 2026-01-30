package com.project.saas_billing_system.repository.subscription;

import com.project.saas_billing_system.model.subscription.Subscription;
import com.project.saas_billing_system.model.subscription.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByOrganizationId(Long organizationId);

    List<Subscription> findByOrganizationIdAndStatus(Long organizationId, SubscriptionStatus status);
}
