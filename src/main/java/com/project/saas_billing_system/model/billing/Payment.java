package com.project.saas_billing_system.model.billing;

import com.project.saas_billing_system.model.base.AuditableEntity;
import com.project.saas_billing_system.model.identity.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String paymentReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount = BigDecimal.ZERO;

    private String currency = "USD";

    private String paymentMethod;

    @Column(name = "external_id")
    private String externalId;
}
