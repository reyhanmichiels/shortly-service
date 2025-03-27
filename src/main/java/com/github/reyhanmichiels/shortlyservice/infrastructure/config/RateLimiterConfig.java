package com.github.reyhanmichiels.shortlyservice.infrastructure.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Value("${rate-limiter.capacity}")
    private long capacity;

    @Value("${rate-limiter.refill-tokens}")
    private long refillTokens;

    @Value("${rate-limiter.refill-duration}")
    private long refillDuration;

    @Bean
    public Bucket bucket() {
        Refill refill = Refill.intervally(refillTokens, Duration.ofSeconds(refillDuration));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

}