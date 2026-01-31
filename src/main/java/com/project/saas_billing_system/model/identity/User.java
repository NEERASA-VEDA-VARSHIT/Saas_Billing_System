package com.project.saas_billing_system.model.identity;

import com.project.saas_billing_system.model.base.AuditableEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@Schema(hidden = true)
public class User extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Schema(hidden = true)
    private String passwordHash;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @Schema(hidden = true)
    private Organization organization;
}
