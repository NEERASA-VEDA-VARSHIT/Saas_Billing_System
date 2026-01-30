package com.project.saas_billing_system.service.billing;

import com.project.saas_billing_system.dto.billing.PaymentResponse;
import com.project.saas_billing_system.event.PaymentEvent;
import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.billing.Payment;
import com.project.saas_billing_system.model.billing.PaymentStatus;
import com.project.saas_billing_system.producer.EventProducer;
import com.project.saas_billing_system.repository.billing.PaymentRepository;
import com.project.saas_billing_system.service.identity.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceService invoiceService;
    private final OrganizationService organizationService;
    private final EventProducer eventProducer;

    @Transactional(readOnly = true)
    public PaymentResponse getById(Long id) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getByOrganizationId(Long organizationId) {
        return paymentRepository.findByOrganizationId(organizationId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentResponse create(Long organizationId, Long invoiceId, BigDecimal amount, String currency, String paymentMethod) {
        organizationService.getById(organizationId);
        Payment p = new Payment();
        p.setPaymentReference("PAY-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase());
        p.setOrganization(organizationService.getById(organizationId));
        if (invoiceId != null) {
            p.setInvoice(invoiceService.getEntityById(invoiceId));
        }
        p.setAmount(amount != null ? amount : BigDecimal.ZERO);
        p.setCurrency(currency != null ? currency : "USD");
        p.setPaymentMethod(paymentMethod);
        p.setStatus(PaymentStatus.PENDING);
        p = paymentRepository.save(p);
        eventProducer.publishPaymentEvent(PaymentEvent.builder()
                .paymentId(p.getId())
                .paymentReference(p.getPaymentReference())
                .organizationId(organizationId)
                .invoiceId(invoiceId)
                .status(p.getStatus())
                .amount(p.getAmount())
                .currency(p.getCurrency())
                .paymentMethod(p.getPaymentMethod())
                .build());
        return toResponse(p);
    }

    private PaymentResponse toResponse(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.setId(p.getId());
        r.setPaymentReference(p.getPaymentReference());
        r.setOrganizationId(p.getOrganization().getId());
        r.setInvoiceId(p.getInvoice() != null ? p.getInvoice().getId() : null);
        r.setStatus(p.getStatus());
        r.setAmount(p.getAmount());
        r.setCurrency(p.getCurrency());
        r.setPaymentMethod(p.getPaymentMethod());
        r.setExternalId(p.getExternalId());
        r.setCreatedAt(p.getCreatedAt());
        return r;
    }
}
