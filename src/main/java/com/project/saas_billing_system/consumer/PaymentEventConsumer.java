package com.project.saas_billing_system.consumer;

import com.project.saas_billing_system.config.kafka.KafkaTopics;
import com.project.saas_billing_system.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    @KafkaListener(topics = KafkaTopics.PAYMENT_EVENTS, groupId = "${spring.kafka.consumer.group-id:saas-billing-group}")
    public void consume(PaymentEvent event) {
        log.info("Received payment event: {} for org {}", event.getPaymentReference(), event.getOrganizationId());
        // Handle payment event (e.g. update invoice, send notification)
    }
}
