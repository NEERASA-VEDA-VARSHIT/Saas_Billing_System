package com.project.saas_billing_system.dto.identity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Organization response")
public class OrganizationResponse {

    @Schema(description = "Organization ID")
    private Long id;

    @Schema(description = "Organization name")
    private String name;

    @Schema(description = "URL-friendly slug")
    private String slug;

    @Schema(description = "Billing email")
    private String billingEmail;

    @Schema(description = "Billing address")
    private String billingAddress;

    @Schema(description = "Tax ID")
    private String taxId;

    @Schema(description = "Whether the organization is active")
    private boolean active;
}
