package com.project.saas_billing_system.controller.v1.organization;

import com.project.saas_billing_system.dto.common.PagedResponse;
import com.project.saas_billing_system.dto.identity.OrganizationCreateRequest;
import com.project.saas_billing_system.dto.identity.OrganizationResponse;
import com.project.saas_billing_system.dto.identity.OrganizationUpdateRequest;
import com.project.saas_billing_system.model.identity.Organization;
import com.project.saas_billing_system.service.identity.OrganizationService;
import com.project.saas_billing_system.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    @Operation(summary = "List organizations (paged & sorted)", description = "Params: page (0-based), size, sort (e.g. name,asc or id,desc). Swagger may send sort as [\"name\",\"asc\"]; both formats are accepted.")
    public ResponseEntity<ApiResponse<PagedResponse<OrganizationResponse>>> list(
            @RequestParam(defaultValue = "0") @Parameter(description = "0-based page") int page,
            @RequestParam(defaultValue = "20") @Parameter(description = "Page size") int size,
            @RequestParam(required = false) @Parameter(description = "Sort: property,direction e.g. name,asc or id,desc") String sort) {
        Sort order = parseSort(sort, "name", Sort.Direction.ASC);
        Pageable pageable = PageRequest.of(page, size, order);
        Page<Organization> pageResult = organizationService.findAll(pageable);
        PagedResponse<OrganizationResponse> paged = PagedResponse.<OrganizationResponse>builder()
                .content(pageResult.getContent().stream().map(this::toResponse).collect(Collectors.toList()))
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .number(pageResult.getNumber())
                .size(pageResult.getSize())
                .first(pageResult.isFirst())
                .last(pageResult.isLast())
                .build();
        return ResponseEntity.ok(ApiResponse.success(paged));
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
    public ResponseEntity<ApiResponse<OrganizationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(toResponse(organizationService.getById(id))));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(toResponse(organizationService.getBySlug(slug))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationResponse>> create(@RequestBody OrganizationCreateRequest request) {
        Organization org = organizationService.create(
                request.getName(),
                request.getBillingEmail(),
                request.getBillingAddress(),
                request.getTaxId());
        return ResponseEntity.status(201).body(ApiResponse.success(toResponse(org)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> update(@PathVariable Long id, @RequestBody OrganizationUpdateRequest request) {
        Organization org = organizationService.update(
                id,
                request.getName(),
                request.getBillingEmail(),
                request.getBillingAddress(),
                request.getTaxId(),
                request.getActive());
        return ResponseEntity.ok(ApiResponse.success(toResponse(org)));
    }

    private OrganizationResponse toResponse(Organization org) {
        return OrganizationResponse.builder()
                .id(org.getId())
                .name(org.getName())
                .slug(org.getSlug())
                .billingEmail(org.getBillingEmail())
                .billingAddress(org.getBillingAddress())
                .taxId(org.getTaxId())
                .active(org.isActive())
                .build();
    }
}
