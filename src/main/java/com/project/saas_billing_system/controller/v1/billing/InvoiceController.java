package com.project.saas_billing_system.controller.v1.billing;

import com.project.saas_billing_system.dto.billing.InvoiceResponse;
import com.project.saas_billing_system.service.billing.InvoiceService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(invoiceService.getById(id)));
    }

    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getByNumber(@PathVariable String invoiceNumber) {
        return ResponseEntity.ok(ApiResponse.success(invoiceService.getByInvoiceNumber(invoiceNumber)));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getByOrganization(@PathVariable Long organizationId) {
        return ResponseEntity.ok(ApiResponse.success(invoiceService.getByOrganizationId(organizationId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponse>> create(@RequestBody Map<String, Object> body) {
        Long orgId = Long.valueOf(body.get("organizationId").toString());
        java.math.BigDecimal amount = body.get("amount") != null ? new java.math.BigDecimal(body.get("amount").toString()) : null;
        java.math.BigDecimal taxAmount = body.get("taxAmount") != null ? new java.math.BigDecimal(body.get("taxAmount").toString()) : null;
        java.time.Instant dueDate = body.get("dueDate") != null ? java.time.Instant.parse(body.get("dueDate").toString()) : null;
        return ResponseEntity.status(201).body(ApiResponse.success(invoiceService.create(orgId, amount, taxAmount, dueDate)));
    }
}
