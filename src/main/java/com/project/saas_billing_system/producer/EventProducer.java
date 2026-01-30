package com.project.saas_billing_system.producer;

import com.project.saas_billing_system.config.kafka.KafkaTopics;
import com.project.saas_billing_system.event.AnalyticsEvent;
import com.project.saas_billing_system.event.NotificationEvent;
import com.project.saas_billing_system.event.PaymentEvent;
import com.project.saas_billing_system.event.SubscriptionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentEvent(PaymentEvent event) {
        kafkaTemplate.send(KafkaTopics.PAYMENT_EVENTS, event.getPaymentReference(), event);
    }

    public void publishSubscriptionEvent(SubscriptionEvent event) {
        kafkaTemplate.send(KafkaTopics.SUBSCRIPTION_EVENTS, String.valueOf(event.getSubscriptionId()), event);
    }

    public void publishNotificationEvent(NotificationEvent event) {
        kafkaTemplate.send(KafkaTopics.NOTIFICATION_EVENTS, event.getRecipientEmail(), event);
    }

    public void publishAnalyticsEvent(AnalyticsEvent event) {
        kafkaTemplate.send(KafkaTopics.ANALYTICS_EVENTS, event.getOrganizationId() + "-" + event.getMetricName(), event);
    }
}
