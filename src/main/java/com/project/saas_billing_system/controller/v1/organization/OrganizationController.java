package com.project.saas_billing_system.controller.v1.organization;

import com.project.saas_billing_system.dto.identity.OrganizationCreateRequest;
import com.project.saas_billing_system.dto.identity.OrganizationResponse;
import com.project.saas_billing_system.dto.identity.OrganizationUpdateRequest;
import com.project.saas_billing_system.model.identity.Organization;
import com.project.saas_billing_system.service.identity.OrganizationService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> list() {
        List<OrganizationResponse> list = organizationService.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list));
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
