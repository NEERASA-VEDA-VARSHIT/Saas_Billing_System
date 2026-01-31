package com.project.saas_billing_system.repository.billing;

import com.project.saas_billing_system.model.billing.Payment;
import com.project.saas_billing_system.model.billing.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(String paymentReference);

    Optional<Payment> findByExternalId(String externalId);

    List<Payment> findByOrganizationId(Long organizationId);

    List<Payment> findByOrganizationIdAndStatus(Long organizationId, PaymentStatus status);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.organization.id = :orgId "
            + "AND p.status = com.project.saas_billing_system.model.billing.PaymentStatus.COMPLETED "
            + "AND (:fromDate IS NULL OR p.createdAt >= :fromDate) "
            + "AND (:toDate IS NULL OR p.createdAt <= :toDate)")
    BigDecimal sumCompletedPaymentsByOrganizationAndDateRange(
            @Param("orgId") Long organizationId,
            @Param("fromDate") Instant fromDate,
            @Param("toDate") Instant toDate);
}
