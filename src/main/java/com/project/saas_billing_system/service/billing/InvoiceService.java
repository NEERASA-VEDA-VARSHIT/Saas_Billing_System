package com.project.saas_billing_system.service.billing;

import com.project.saas_billing_system.dto.billing.InvoiceResponse;
import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.billing.Invoice;
import com.project.saas_billing_system.model.billing.InvoiceStatus;
import com.project.saas_billing_system.repository.billing.InvoiceRepository;
import com.project.saas_billing_system.service.identity.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrganizationService organizationService;

    @Transactional(readOnly = true)
    public Invoice getEntityById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
    }

    @Transactional(readOnly = true)
    public InvoiceResponse getById(Long id) {
        return toResponse(getEntityById(id));
    }

    @Transactional(readOnly = true)
    public InvoiceResponse getByInvoiceNumber(String invoiceNumber) {
        Invoice inv = invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "invoiceNumber", invoiceNumber));
        return toResponse(inv);
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getByOrganizationId(Long organizationId) {
        return invoiceRepository.findByOrganizationId(organizationId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InvoiceResponse create(Long organizationId, BigDecimal amount, BigDecimal taxAmount, Instant dueDate) {
        organizationService.getById(organizationId);
        Invoice inv = new Invoice();
        inv.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        inv.setOrganization(organizationService.getById(organizationId));
        inv.setAmount(amount != null ? amount : BigDecimal.ZERO);
        inv.setTaxAmount(taxAmount != null ? taxAmount : BigDecimal.ZERO);
        inv.setDueDate(dueDate);
        inv.setStatus(InvoiceStatus.PENDING);
        inv = invoiceRepository.save(inv);
        return toResponse(inv);
    }

    private InvoiceResponse toResponse(Invoice inv) {
        InvoiceResponse r = new InvoiceResponse();
        r.setId(inv.getId());
        r.setInvoiceNumber(inv.getInvoiceNumber());
        r.setOrganizationId(inv.getOrganization().getId());
        r.setSubscriptionId(inv.getSubscription() != null ? inv.getSubscription().getId() : null);
        r.setStatus(inv.getStatus());
        r.setAmount(inv.getAmount());
        r.setTaxAmount(inv.getTaxAmount());
        r.setDueDate(inv.getDueDate());
        r.setPaidAt(inv.getPaidAt());
        r.setCreatedAt(inv.getCreatedAt());
        return r;
    }
}
