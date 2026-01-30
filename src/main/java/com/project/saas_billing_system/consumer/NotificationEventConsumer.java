package com.project.saas_billing_system.consumer;

import com.project.saas_billing_system.config.kafka.KafkaTopics;
import com.project.saas_billing_system.event.NotificationEvent;
import com.project.saas_billing_system.service.notification.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final EmailNotificationService emailNotificationService;

    @KafkaListener(topics = KafkaTopics.NOTIFICATION_EVENTS, groupId = "${spring.kafka.consumer.group-id:saas-billing-group}")
    public void consume(NotificationEvent event) {
        log.info("Sending notification to {}", event.getRecipientEmail());
        emailNotificationService.sendNotification(event);
    }
}
