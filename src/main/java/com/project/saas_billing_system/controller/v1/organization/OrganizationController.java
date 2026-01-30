package com.project.saas_billing_system.controller.v1.organization;

import com.project.saas_billing_system.model.identity.Organization;
import com.project.saas_billing_system.service.identity.OrganizationService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Organization>>> list() {
        return ResponseEntity.ok(ApiResponse.success(organizationService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Organization>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(organizationService.getById(id)));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<Organization>> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(organizationService.getBySlug(slug)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Organization>> create(@RequestBody Map<String, String> body) {
        Organization org = organizationService.create(
                body.get("name"),
                body.get("billingEmail"),
                body.get("billingAddress"),
                body.get("taxId"));
        return ResponseEntity.status(201).body(ApiResponse.success(org));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Organization>> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Organization org = organizationService.update(
                id,
                (String) body.get("name"),
                (String) body.get("billingEmail"),
                (String) body.get("billingAddress"),
                (String) body.get("taxId"),
                body.get("active") != null ? (Boolean) body.get("active") : null);
        return ResponseEntity.ok(ApiResponse.success(org));
    }
}
