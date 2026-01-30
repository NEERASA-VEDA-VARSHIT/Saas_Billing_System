package com.project.saas_billing_system.dto.webhook;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PaymentWebhookRequest {

    private String eventType;
    private String eventId;
    private Map<String, Object> data;
    private Long createdAt;
}
