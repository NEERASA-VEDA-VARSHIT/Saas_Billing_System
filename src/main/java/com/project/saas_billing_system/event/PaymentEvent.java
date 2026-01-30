package com.project.saas_billing_system.event;

import com.project.saas_billing_system.model.billing.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

    private Long paymentId;
    private String paymentReference;
    private Long organizationId;
    private Long invoiceId;
    private PaymentStatus status;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
}
