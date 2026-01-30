package com.project.saas_billing_system.controller.v1.webhook;

import com.project.saas_billing_system.dto.webhook.PaymentWebhookRequest;
import com.project.saas_billing_system.service.webhook.WebhookService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final WebhookService webhookService;

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<String>> paymentWebhook(@RequestBody PaymentWebhookRequest request) {
        try {
            String payload = request.getData() != null ? request.getData().toString() : "";
            webhookService.logEvent(request.getEventType(), payload, "payment-gateway");
            return ResponseEntity.ok(ApiResponse.success("Webhook received"));
        } catch (Exception e) {
            log.error("Webhook processing error", e);
            return ResponseEntity.status(500).body(ApiResponse.error("Webhook processing failed"));
        }
    }
}
