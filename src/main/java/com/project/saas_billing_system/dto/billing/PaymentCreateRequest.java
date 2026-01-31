package com.project.saas_billing_system.dto.billing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a payment")
public class PaymentCreateRequest {

    @Schema(description = "Organization ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long organizationId;

    @Schema(description = "Invoice ID (optional)")
    private Long invoiceId;

    @Schema(description = "Amount")
    private BigDecimal amount;

    @Schema(description = "Currency code")
    private String currency;

    @Schema(description = "Payment method")
    private String paymentMethod;
}
