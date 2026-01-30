package com.project.saas_billing_system.config.kafka;

public final class KafkaTopics {

    public static final String PAYMENT_EVENTS = "payment-events";
    public static final String SUBSCRIPTION_EVENTS = "subscription-events";
    public static final String NOTIFICATION_EVENTS = "notification-events";
    public static final String ANALYTICS_EVENTS = "analytics-events";

    private KafkaTopics() {
    }
}
