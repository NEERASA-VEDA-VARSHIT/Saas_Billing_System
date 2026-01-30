package com.project.saas_billing_system.dto.analytics;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AnalyticsResponse {

    private Long organizationId;
    private String period;
    private Map<String, Double> usageByMetric;
    private Double totalUsage;
    private Map<String, Object> summary;
}
