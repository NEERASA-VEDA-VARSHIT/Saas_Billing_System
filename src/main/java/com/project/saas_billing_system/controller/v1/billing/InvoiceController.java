package com.project.saas_billing_system.controller.v1.billing;

import com.project.saas_billing_system.dto.billing.InvoiceCreateRequest;
import com.project.saas_billing_system.dto.billing.InvoiceResponse;
import com.project.saas_billing_system.dto.common.PagedResponse;
import com.project.saas_billing_system.model.billing.InvoiceStatus;
import com.project.saas_billing_system.service.billing.InvoiceService;
import com.project.saas_billing_system.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/search")
    @Operation(summary = "Search invoices (complex query, paged & sorted)", description = "Filter by org, optional status/due range. Params: page, size, sort (e.g. dueDate,desc or amount,asc). Swagger may send sort as [\"dueDate\",\"desc\"]; both formats are accepted.")
    public ResponseEntity<ApiResponse<PagedResponse<InvoiceResponse>>> search(
            @RequestParam @Parameter(description = "Organization ID", required = true) Long organizationId,
            @RequestParam(required = false) @Parameter(description = "Filter by status") InvoiceStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(description = "Due date from (ISO-8601)") Instant dueFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Parameter(description = "Due date to (ISO-8601)") Instant dueTo,
            @RequestParam(defaultValue = "0") @Parameter(description = "0-based page") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size") int size,
            @RequestParam(required = false) @Parameter(description = "Sort: property,direction e.g. dueDate,desc or amount,asc") String sort) {
        Sort order = parseSort(sort, "dueDate", Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(page, size, order);
        return ResponseEntity.ok(ApiResponse.success(invoiceService.searchInvoices(organizationId, status, dueFrom, dueTo, pageable)));
    }

    private Sort parseSort(String sort, String defaultProperty, Sort.Direction defaultDirection) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(defaultDirection, defaultProperty);
        }
        String normalized = sort.replace("[", "").replace("]", "").replace("\"", "").trim();
        String[] parts = normalized.split(",");
        if (parts.length >= 2) {
            String property = parts[0].trim();
            String direction = parts[1].trim();
            return Sort.by("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC, property);
        }
        if (parts.length == 1 && !parts[0].isBlank()) {
            return Sort.by(defaultDirection, parts[0].trim());
        }
        return Sort.by(defaultDirection, defaultProperty);
    }

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
