package com.project.saas_billing_system.repository.analytics;

import com.project.saas_billing_system.model.analytics.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface UsageRecordRepository extends JpaRepository<UsageRecord, Long> {

    List<UsageRecord> findByOrganizationId(Long organizationId);

    List<UsageRecord> findByOrganizationIdAndRecordedAtBetween(Long organizationId, Instant start, Instant end);

    List<UsageRecord> findByOrganizationIdAndMetricName(Long organizationId, String metricName);
}
