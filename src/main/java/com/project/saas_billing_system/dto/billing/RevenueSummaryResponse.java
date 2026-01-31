package com.project.saas_billing_system.dto.billing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Revenue summary for an organization in a date range (completed payments)")
public class RevenueSummaryResponse {

    @Schema(description = "Organization ID")
    private Long organizationId;

    @Schema(description = "Total revenue (sum of completed payment amounts)")
    private BigDecimal totalRevenue;

    @Schema(description = "Start of date range (optional filter)")
    private Instant fromDate;

    @Schema(description = "End of date range (optional filter)")
    private Instant toDate;

    @Schema(description = "Currency")
    private String currency;
}
