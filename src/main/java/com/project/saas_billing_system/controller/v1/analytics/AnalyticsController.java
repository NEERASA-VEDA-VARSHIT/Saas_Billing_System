package com.project.saas_billing_system.controller.v1.analytics;

import com.project.saas_billing_system.dto.analytics.AnalyticsResponse;
import com.project.saas_billing_system.service.analytics.AnalyticsService;
import com.project.saas_billing_system.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/usage/{organizationId}")
    public ResponseEntity<ApiResponse<AnalyticsResponse>> getUsage(
            @PathVariable Long organizationId,
            @RequestParam(defaultValue = "month") String period,
            @RequestParam(defaultValue = "UTC") String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getUsageAnalytics(organizationId, period, zoneId)));
    }
}
