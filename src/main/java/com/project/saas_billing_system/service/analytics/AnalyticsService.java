package com.project.saas_billing_system.service.analytics;

import com.project.saas_billing_system.dto.analytics.AnalyticsResponse;
import com.project.saas_billing_system.model.analytics.UsageRecord;
import com.project.saas_billing_system.repository.analytics.UsageRecordRepository;
import com.project.saas_billing_system.service.identity.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final UsageRecordRepository usageRecordRepository;
    private final OrganizationService organizationService;

    @Transactional(readOnly = true)
    public AnalyticsResponse getUsageAnalytics(Long organizationId, String period, ZoneId zoneId) {
        organizationService.getById(organizationId);
        Instant start;
        Instant end = Instant.now();
        if ("month".equalsIgnoreCase(period)) {
            start = end.minusSeconds(30L * 24 * 3600);
        } else if ("week".equalsIgnoreCase(period)) {
            start = end.minusSeconds(7L * 24 * 3600);
        } else {
            start = end.minusSeconds(24 * 3600);
        }
        List<UsageRecord> records = usageRecordRepository.findByOrganizationIdAndRecordedAtBetween(organizationId, start, end);
        Map<String, Double> usageByMetric = records.stream()
                .collect(Collectors.groupingBy(UsageRecord::getMetricName,
                        Collectors.summingDouble(UsageRecord::getQuantity)));
        double totalUsage = usageByMetric.values().stream().mapToDouble(Double::doubleValue).sum();
        Map<String, Object> summary = new HashMap<>();
        summary.put("recordCount", records.size());
        summary.put("metricsCount", usageByMetric.size());
        AnalyticsResponse response = new AnalyticsResponse();
        response.setOrganizationId(organizationId);
        response.setPeriod(period);
        response.setUsageByMetric(usageByMetric);
        response.setTotalUsage(totalUsage);
        response.setSummary(summary);
        return response;
    }
}
