package edu.java.service.client;

import edu.java.response.stackoverflow.StackOverflowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StackOverflowService {
    private final WebClient stackOverflowClient;

    public Mono<StackOverflowResponse> fetchSofQuestion(Long questionId) {
        return stackOverflowClient.get()
            .uri(String.join("/",
                questionId.toString(), "?site=stackoverflow"
            ))
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .doOnError(error -> log.error("Error has occurred {}", error.getMessage()));
    }
}
