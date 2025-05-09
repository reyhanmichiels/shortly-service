package com.github.reyhanmichiels.shortlyservice.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange constants
    public static final String ANALYTICS_EXCHANGE = "analytics-exchange";

    // Queue constants
    public static final String SHORT_URL_CLICK_ANALYTICS_QUEUE = "short-url-click-analytics-queue";

    // Routing key constants
    public static final String ANALYTIC_CREATED_ROUTING_KEY = "analytic.created";

    @Bean
    public TopicExchange analyticTopicExchange() {
        return new TopicExchange(ANALYTICS_EXCHANGE);
    }

    @Bean
    public Queue shortUrlClickAnalyticsQueue() {
        return new Queue(SHORT_URL_CLICK_ANALYTICS_QUEUE);
    }

    @Bean
    public Binding shortUrlClickAnalyticsBinding() {
        return BindingBuilder
                .bind(shortUrlClickAnalyticsQueue())
                .to(analyticTopicExchange())
                .with(ANALYTIC_CREATED_ROUTING_KEY);
    }
}

