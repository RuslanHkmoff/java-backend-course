package edu.java.service.client;

import edu.java.response.github.GithubResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubService {

    private final WebClient githubClient;

    public Mono<GithubResponse> fetchGithubRepository(final String repoOwner, final String repoName) {
        return githubClient
            .get()
            .uri(String.join("/", repoOwner, repoName))
            .retrieve()
            .bodyToMono(GithubResponse.class)
            .doOnError(error -> log.error("Error has occurred {}", error.getMessage()));
    }
}
