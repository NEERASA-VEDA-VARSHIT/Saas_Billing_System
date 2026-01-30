package com.project.saas_billing_system.config.rate_limit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitConfig {

    @Bean
    public RateLimitFilter rateLimitFilter(RateLimitStore rateLimitStore) {
        return new RateLimitFilter(rateLimitStore);
    }
}
