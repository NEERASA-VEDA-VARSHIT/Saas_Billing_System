package com.project.saas_billing_system.repository.billing;

import com.project.saas_billing_system.model.billing.Payment;
import com.project.saas_billing_system.model.billing.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(String paymentReference);

    Optional<Payment> findByExternalId(String externalId);

    List<Payment> findByOrganizationId(Long organizationId);

    List<Payment> findByOrganizationIdAndStatus(Long organizationId, PaymentStatus status);
}
