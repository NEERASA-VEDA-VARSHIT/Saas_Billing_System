package com.project.saas_billing_system.controller.v1.billing;

import com.project.saas_billing_system.dto.billing.InvoiceCreateRequest;
import com.project.saas_billing_system.dto.billing.InvoiceResponse;
import com.project.saas_billing_system.service.billing.InvoiceService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<InvoiceResponse>> create(@RequestBody InvoiceCreateRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success(invoiceService.create(
                request.getOrganizationId(),
                request.getAmount(),
                request.getTaxAmount(),
                request.getDueDate())));
    }
}
