package com.project.saas_billing_system.controller.v1.billing;

import com.project.saas_billing_system.dto.billing.PaymentCreateRequest;
import com.project.saas_billing_system.dto.billing.PaymentResponse;
import com.project.saas_billing_system.service.billing.PaymentService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<PaymentResponse>> create(@RequestBody PaymentCreateRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success(paymentService.create(
                request.getOrganizationId(),
                request.getInvoiceId(),
                request.getAmount(),
                request.getCurrency(),
                request.getPaymentMethod())));
    }
}
