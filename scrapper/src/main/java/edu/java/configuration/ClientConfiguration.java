package edu.java.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final ApplicationConfig config;

    @Bean
    public WebClient githubClient() {
        return WebClient
            .builder()
            .baseUrl(config.baseUrl().github())
            .build();
    }

    @Bean
    WebClient stackOverflowClient() {
        return WebClient
            .builder()
            .baseUrl(config.baseUrl().stackoverflow())
            .build();
    }
}
