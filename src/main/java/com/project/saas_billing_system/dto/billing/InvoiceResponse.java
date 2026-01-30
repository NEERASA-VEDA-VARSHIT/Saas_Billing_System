package com.project.saas_billing_system.dto.billing;

import com.project.saas_billing_system.model.billing.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class InvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private Long organizationId;
    private Long subscriptionId;
    private InvoiceStatus status;
    private BigDecimal amount;
    private BigDecimal taxAmount;
    private Instant dueDate;
    private Instant paidAt;
    private Instant createdAt;
}
