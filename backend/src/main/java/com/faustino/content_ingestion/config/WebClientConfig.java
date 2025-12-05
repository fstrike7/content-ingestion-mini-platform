package com.faustino.content_ingestion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient contentProcessorWebClient(
            @Value("${content.processor.base-url:http://localhost:8000}") String baseUrl
    ) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                // Keep memory usage predictable while allowing JSON payloads
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                        .build())
                .build();
    }
}
