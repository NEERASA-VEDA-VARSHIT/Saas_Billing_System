package com.project.saas_billing_system.controller.v1.billing;

import com.project.saas_billing_system.dto.billing.PaymentCreateRequest;
import com.project.saas_billing_system.dto.billing.PaymentResponse;
import com.project.saas_billing_system.dto.billing.RevenueSummaryResponse;
import com.project.saas_billing_system.service.billing.PaymentService;
import com.project.saas_billing_system.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/revenue-summary")
    @Operation(summary = "Revenue summary (complex query)", description = "Sum of completed payments for an organization in an optional date range.")
    public ResponseEntity<ApiResponse<RevenueSummaryResponse>> revenueSummary(
            @RequestParam @Parameter(description = "Organization ID", required = true) Long organizationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(description = "Start date (ISO-8601)") Instant fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(description = "End date (ISO-8601)") Instant toDate) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.getRevenueSummary(organizationId, fromDate, toDate)));
    }

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
