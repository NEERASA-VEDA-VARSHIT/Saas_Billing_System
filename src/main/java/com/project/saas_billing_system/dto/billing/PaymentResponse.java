package com.project.saas_billing_system.dto.billing;

import com.project.saas_billing_system.model.billing.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class PaymentResponse {

    private Long id;
    private String paymentReference;
    private Long organizationId;
    private Long invoiceId;
    private PaymentStatus status;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String externalId;
    private Instant createdAt;
}
