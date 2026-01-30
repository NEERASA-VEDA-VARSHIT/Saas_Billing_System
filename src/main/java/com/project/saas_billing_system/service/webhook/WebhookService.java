package com.project.saas_billing_system.service.webhook;

import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.webhook.WebhookEventLog;
import com.project.saas_billing_system.repository.webhook.WebhookEventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookEventLogRepository webhookEventLogRepository;

    @Transactional
    public WebhookEventLog logEvent(String eventType, String payload, String source) {
        WebhookEventLog log = new WebhookEventLog();
        log.setEventType(eventType);
        log.setPayload(payload);
        log.setSource(source);
        log.setProcessingStatus("PENDING");
        return webhookEventLogRepository.save(log);
    }

    @Transactional
    public WebhookEventLog markProcessed(Long id, String status, String errorMessage) {
        WebhookEventLog log = webhookEventLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WebhookEventLog", "id", id));
        log.setProcessedAt(Instant.now());
        log.setProcessingStatus(status);
        log.setErrorMessage(errorMessage);
        return webhookEventLogRepository.save(log);
    }
}
