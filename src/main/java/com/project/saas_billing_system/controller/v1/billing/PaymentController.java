package com.project.saas_billing_system.controller.v1.billing;

import com.project.saas_billing_system.dto.billing.PaymentResponse;
import com.project.saas_billing_system.service.billing.PaymentService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getById(id)));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getByOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getByOrganizationId(organizationId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> create(@RequestBody Map<String, Object> body) {
        Long orgId = Long.valueOf(body.get("organizationId").toString());
        Long invoiceId = body.get("invoiceId") != null ? Long.valueOf(body.get("invoiceId").toString()) : null;
        java.math.BigDecimal amount = body.get("amount") != null ? new java.math.BigDecimal(body.get("amount").toString()) : null;
        String currency = (String) body.get("currency");
        String paymentMethod = (String) body.get("paymentMethod");
        return ResponseEntity.status(201).body(ApiResponse.success(paymentService.create(orgId, invoiceId, amount, currency, paymentMethod)));
    }
}
