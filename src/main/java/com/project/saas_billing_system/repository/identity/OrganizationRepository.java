package com.project.saas_billing_system.repository.identity;

import com.project.saas_billing_system.model.identity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByName(String name);
}
