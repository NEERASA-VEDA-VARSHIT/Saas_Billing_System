package com.project.saas_billing_system.dto.billing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create an invoice")
public class InvoiceCreateRequest {

    @Schema(description = "Organization ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long organizationId;

    @Schema(description = "Amount")
    private BigDecimal amount;

    @Schema(description = "Tax amount")
    private BigDecimal taxAmount;

    @Schema(description = "Due date (ISO-8601)")
    private Instant dueDate;
}
