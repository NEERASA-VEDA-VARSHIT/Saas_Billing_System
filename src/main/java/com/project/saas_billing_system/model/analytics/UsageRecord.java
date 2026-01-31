package com.project.saas_billing_system.model.analytics;

import com.project.saas_billing_system.model.base.AuditableEntity;
import com.project.saas_billing_system.model.identity.Organization;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usage_records")
@Schema(hidden = true)
public class UsageRecord extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    private String metricName;

    @Column(nullable = false)
    private Double quantity = 0.0;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    private String metadata;
}
