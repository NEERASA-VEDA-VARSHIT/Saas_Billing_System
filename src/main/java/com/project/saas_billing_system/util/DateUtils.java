package com.project.saas_billing_system.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private DateUtils() {
    }

    public static Instant startOfDay(Instant instant, ZoneId zoneId) {
        return instant.atZone(zoneId).toLocalDate().atStartOfDay(zoneId).toInstant();
    }

    public static Instant endOfDay(Instant instant, ZoneId zoneId) {
        return instant.atZone(zoneId).toLocalDate().plusDays(1).atStartOfDay(zoneId).toInstant();
    }

    public static String formatIsoDate(Instant instant, ZoneId zoneId) {
        LocalDate date = instant.atZone(zoneId).toLocalDate();
        return date.format(ISO_FORMATTER);
    }

    public static Instant parseIsoDate(String dateStr, ZoneId zoneId) {
        LocalDate date = LocalDate.parse(dateStr, ISO_FORMATTER);
        return date.atStartOfDay(zoneId).toInstant();
    }
}
