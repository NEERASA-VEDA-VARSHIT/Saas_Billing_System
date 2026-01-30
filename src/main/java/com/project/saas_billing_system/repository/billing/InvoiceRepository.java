package com.project.saas_billing_system.repository.billing;

import com.project.saas_billing_system.model.billing.Invoice;
import com.project.saas_billing_system.model.billing.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByOrganizationId(Long organizationId);

    List<Invoice> findByOrganizationIdAndStatus(Long organizationId, InvoiceStatus status);
}
