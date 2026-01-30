package com.project.saas_billing_system.repository.webhook;

import com.project.saas_billing_system.model.webhook.WebhookEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookEventLogRepository extends JpaRepository<WebhookEventLog, Long> {

    List<WebhookEventLog> findByEventType(String eventType);

    List<WebhookEventLog> findByProcessingStatus(String processingStatus);
}
