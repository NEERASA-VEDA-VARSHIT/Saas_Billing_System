package com.project.saas_billing_system.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEvent {

    private Long organizationId;
    private String metricName;
    private Double quantity;
    private Instant recordedAt;
    private String metadata;
}
