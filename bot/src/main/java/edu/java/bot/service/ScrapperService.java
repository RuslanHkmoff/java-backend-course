package edu.java.bot.service;

import edu.java.models.request.AddLinkRequest;
import edu.java.models.request.RemoveLinkRequest;
import edu.java.models.response.LinkResponse;
import edu.java.models.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperService {
    private static final String LINKS_URI = "/links/%d";
    private static final String TG_URI = "tg-chat/%d";

    private final WebClient scrapperClient;

    public LinkResponse addLink(Long id, String link) {
        return scrapperClient
            .post()
            .uri(String.format(LINKS_URI, id))
            .body(BodyInserters.fromValue(new AddLinkRequest(link)))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .doOnError(error -> log.error("Error while addLink, {}", error.getMessage()))
            .onErrorResume((error) -> Mono.empty())
            .block();
    }

    public ListLinksResponse getAllLinks(Long id) {
        return scrapperClient
            .get()
            .uri(String.format(LINKS_URI, id))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .doOnError(error -> log.error("Error while getAllLinks, {}", error.getMessage()))
            .onErrorResume((error) -> Mono.empty())
            .block();
    }

    public LinkResponse deleteLink(Long id, String link) {
        return scrapperClient
            .method(HttpMethod.DELETE)
            .uri(String.format(LINKS_URI, id))
            .body(BodyInserters.fromValue(new RemoveLinkRequest(link)))
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .doOnError(error -> log.error("Error while deleteLink, {}", error.getMessage()))
            .onErrorResume((error) -> Mono.empty())
            .block();
    }

    public boolean registerChat(Long id) {
        return scrapperClient
            .post()
            .uri(String.format(TG_URI, id))
            .retrieve()
            .toBodilessEntity()
            .doOnError(error -> log.error("Error while registerChat, {}", error.getMessage()))
            .blockOptional()
            .isPresent();
    }

    public boolean deleteChat(Long id) {
        return scrapperClient
            .method(HttpMethod.DELETE)
            .uri(String.format(TG_URI, id))
            .retrieve()
            .toBodilessEntity()
            .doOnError(error -> log.error("Error while deleteChat, {}", error.getMessage()))
            .blockOptional()
            .isPresent();
    }
}
