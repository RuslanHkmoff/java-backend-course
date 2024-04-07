package edu.java.sender;

import edu.java.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@RequiredArgsConstructor
@Slf4j
public class HttpUpdateSender implements UpdateSender {
    private final WebClient botClient;
    private final Retry retryStrategy;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        botClient
            .post()
            .uri("/updates")
            .body(Mono.just(request), LinkUpdateRequest.class)
            .retrieve()
            .bodyToMono(Void.class)
            .retryWhen(retryStrategy)
            .doOnError(err -> log.info("Error while send update {}, {}", err.getMessage(), err.getStackTrace()))
            .onErrorResume(err -> Mono.empty())
            .block();
    }
}
