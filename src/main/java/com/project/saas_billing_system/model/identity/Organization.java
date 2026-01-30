package com.project.saas_billing_system.model.identity;

import com.project.saas_billing_system.model.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "organizations")
public class Organization extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    private String billingEmail;

    private String billingAddress;

    private String taxId;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
