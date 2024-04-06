package edu.java.bot.configuration;

import java.time.Duration;
import java.util.Set;
import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
public class RetryConfig {
    private final Set<Integer> codes;
    private final int limit;
    private final String backoff;
    private final long delay;

    public RetryConfig(ApplicationConfig applicationConfig) {
        codes = applicationConfig.retryPolicy().codes();
        limit = applicationConfig.retryPolicy().limit();
        backoff = applicationConfig.retryPolicy().backoff();
        delay = applicationConfig.retryPolicy().delay();
    }

    @Bean
    public Retry retryStrategy() {
        return switch (backoff) {
            case "constant" -> Retry.fixedDelay(limit, Duration.ofMillis(delay))
                .filter(isRetryableSpecException());
            case "linear" -> Retry.from(
                t -> Flux.deferContextual(
                    contextView ->
                        t.contextWrite(contextView)
                            .filter(isRetryableSignalException())
                            .concatMap(retrySignal -> {
                                if (retrySignal.totalRetries() > limit) {
                                    return Mono.empty();
                                }
                                if (retrySignal.failure() == null) {
                                    return Mono.error(IllegalStateException::new);
                                }
                                return Mono.delay(Duration.ofMillis(retrySignal.totalRetries() * delay));
                            })
                )
            );
            case "exponential" -> Retry.backoff(limit, Duration.ofMillis(delay))
                .filter(isRetryableSpecException());
            default -> throw new IllegalArgumentException("Unsupported backoff policy: " + backoff);
        };
    }

    private Predicate<? super Throwable> isRetryableSpecException() {
        return throwable -> {
            HttpClientErrorException error = (HttpClientErrorException) throwable;
            return codes.contains(error.getStatusCode().value());
        };
    }

    private Predicate<? super Retry.RetrySignal> isRetryableSignalException() {
        return retrySignal -> {
            HttpClientErrorException error = (HttpClientErrorException) retrySignal.failure();
            return codes.contains(error.getStatusCode().value());
        };
    }
}

