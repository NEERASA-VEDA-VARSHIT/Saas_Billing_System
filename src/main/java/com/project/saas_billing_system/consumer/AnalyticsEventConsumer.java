package com.project.saas_billing_system.consumer;

import com.project.saas_billing_system.config.kafka.KafkaTopics;
import com.project.saas_billing_system.event.AnalyticsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsEventConsumer {

    @KafkaListener(topics = KafkaTopics.ANALYTICS_EVENTS, groupId = "${spring.kafka.consumer.group-id:saas-billing-group}")
    public void consume(AnalyticsEvent event) {
        log.info("Received analytics event: {} for org {}", event.getMetricName(), event.getOrganizationId());
    }
}
