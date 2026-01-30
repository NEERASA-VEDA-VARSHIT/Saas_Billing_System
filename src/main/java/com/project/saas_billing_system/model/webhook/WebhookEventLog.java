package com.project.saas_billing_system.model.webhook;

import com.project.saas_billing_system.model.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "webhook_event_logs")
public class WebhookEventLog extends AuditableEntity {

    @Column(nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private String source;

    @Column(name = "processed_at")
    private java.time.Instant processedAt;

    @Column(name = "processing_status")
    private String processingStatus;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}
