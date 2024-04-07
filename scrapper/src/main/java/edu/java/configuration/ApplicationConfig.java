package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    BaseUrl baseUrl,
    @Bean
    @NotNull
    Scheduler scheduler,
    String dbAccessType,
    String senderType,
    @NotNull
    RetryPolicy retryPolicy,
    @NotNull
    RateLimit rateLimit,
    Kafka kafka,
    Topic topic
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record BaseUrl(@NotNull String github, @NotNull String stackoverflow, @NotNull String bot) {
    }

    public record RetryPolicy(@NotNull Set<Integer> codes, @NotNull String backoff, int limit, long delay) {
    }

    public record RateLimit(int limit, int delay) {
    }

    public record Kafka(
        String bootstrapServers,
        String clientId,
        String askMode,
        Duration deliveryTimeout,
        Integer lingerMs,
        Integer batchSize,
        Integer maxInFlightPerConnection,
        Boolean enableIdempotence,
        Topic topic
    ) {
    }

    public record Topic(
        String name,
        Integer partitions,
        Integer replicas
    ) {
    }
}
