package com.project.saas_billing_system.dto.identity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create an organization")
public class OrganizationCreateRequest {

    @Schema(description = "Organization name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Billing email")
    private String billingEmail;

    @Schema(description = "Billing address")
    private String billingAddress;

    @Schema(description = "Tax ID")
    private String taxId;
}
