package com.project.saas_billing_system.service.identity;

import com.project.saas_billing_system.dto.common.PagedResponse;
import com.project.saas_billing_system.exception.BusinessException;
import com.project.saas_billing_system.exception.ResourceNotFoundException;
import com.project.saas_billing_system.model.identity.Organization;
import com.project.saas_billing_system.repository.identity.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional
    public Organization create(String name, String billingEmail, String billingAddress, String taxId) {
        String slug = name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
        if (organizationRepository.existsBySlug(slug)) {
            slug = slug + "-" + UUID.randomUUID().toString().substring(0, 8);
        }
        Organization org = new Organization();
        org.setName(name);
        org.setSlug(slug);
        org.setBillingEmail(billingEmail);
        org.setBillingAddress(billingAddress);
        org.setTaxId(taxId);
        return organizationRepository.save(org);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "organizations", key = "#id")
    public Organization getById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", id));
    }

    @Transactional(readOnly = true)
    public Organization getBySlug(String slug) {
        return organizationRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "slug", slug));
    }

    @Transactional(readOnly = true)
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Organization> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }

    @Transactional
    public Organization update(Long id, String name, String billingEmail, String billingAddress, String taxId, Boolean active) {
        Organization org = getById(id);
        if (name != null) org.setName(name);
        if (billingEmail != null) org.setBillingEmail(billingEmail);
        if (billingAddress != null) org.setBillingAddress(billingAddress);
        if (taxId != null) org.setTaxId(taxId);
        if (active != null) org.setActive(active);
        return organizationRepository.save(org);
    }
}
