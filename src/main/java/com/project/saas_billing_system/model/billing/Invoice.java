package com.project.saas_billing_system.model.billing;

import com.project.saas_billing_system.model.base.AuditableEntity;
import com.project.saas_billing_system.model.identity.Organization;
import com.project.saas_billing_system.model.subscription.Subscription;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status = InvoiceStatus.DRAFT;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(precision = 19, scale = 4)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "paid_at")
    private Instant paidAt;
}
