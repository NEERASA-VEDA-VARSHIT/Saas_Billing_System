package com.project.saas_billing_system.config.rate_limit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In-memory rate limiter using a fixed window per client key (e.g. IP).
 * For production at scale, consider Redis-based rate limiting (e.g. Bucket4j + Redis).
 */
@Component
public class RateLimitStore {

    private final RateLimitProperties properties;
    private final long windowSecondsMs;
    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

    public RateLimitStore(RateLimitProperties properties) {
        this.properties = properties;
        this.windowSecondsMs = properties.getWindowSeconds() * 1000L;
    }

    /**
     * Tries to acquire a permit for the given key. Returns true if within limit, false if rate exceeded.
     */
    public boolean tryAcquire(String key) {
        if (!properties.isEnabled()) {
            return true;
        }
        long now = System.currentTimeMillis();

        Window window = windows.compute(key, (k, existing) -> {
            if (existing == null || (now - existing.startMs >= windowSecondsMs)) {
                return new Window(now, new AtomicInteger(0));
            }
            return existing;
        });

        int previous = window.count.getAndIncrement();
        return previous < properties.getMaxRequestsPerWindow();
    }

    private static final class Window {
        final long startMs;
        final AtomicInteger count;

        Window(long startMs, AtomicInteger count) {
            this.startMs = startMs;
            this.count = count;
        }
    }
}
