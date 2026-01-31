package com.project.saas_billing_system.consumer;

import com.project.saas_billing_system.config.kafka.KafkaTopics;
import com.project.saas_billing_system.event.SubscriptionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionEventConsumer {

    @KafkaListener(topics = KafkaTopics.SUBSCRIPTION_EVENTS, groupId = "${spring.kafka.consumer.group-id:saas-billing-group}")
    public void consume(SubscriptionEvent event) {
        log.info("Received subscription event: {} for org {}", event.getSubscriptionId(), event.getOrganizationId());
    }
}
