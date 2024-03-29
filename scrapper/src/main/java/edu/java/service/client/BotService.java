package edu.java.service.client;

import edu.java.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService {
    private final WebClient botClient;
    private final Retry retryStrategy;

    public void sendUpdate(LinkUpdateRequest request) {
        botClient
            .post()
            .uri("/updates")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .toBodilessEntity()
            .doOnError(error -> log.error("Error while send update, {}", error.getMessage()))
            .onErrorResume((error) -> Mono.empty())
            .retryWhen(retryStrategy)
            .block();
    }
}
