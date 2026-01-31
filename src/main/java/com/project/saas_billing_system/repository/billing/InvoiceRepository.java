package com.project.saas_billing_system.repository.billing;

import com.project.saas_billing_system.model.billing.Invoice;
import com.project.saas_billing_system.model.billing.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByOrganizationId(Long organizationId);

    List<Invoice> findByOrganizationIdAndStatus(Long organizationId, InvoiceStatus status);

    @Query("SELECT i FROM Invoice i WHERE i.organization.id = :orgId "
            + "AND (:status IS NULL OR i.status = :status) "
            + "AND (:dueFrom IS NULL OR i.dueDate >= :dueFrom) "
            + "AND (:dueTo IS NULL OR i.dueDate <= :dueTo) ")
    Page<Invoice> findInvoicesByOrganizationAndFilters(
            @Param("orgId") Long organizationId,
            @Param("status") InvoiceStatus status,
            @Param("dueFrom") Instant dueFrom,
            @Param("dueTo") Instant dueTo,
            Pageable pageable);
}
