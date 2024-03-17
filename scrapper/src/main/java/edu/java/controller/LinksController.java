package edu.java.controller;

import edu.java.model.Link;
import edu.java.models.request.AddLinkRequest;
import edu.java.models.request.RemoveLinkRequest;
import edu.java.models.response.ApiErrorResponse;
import edu.java.models.response.LinkResponse;
import edu.java.models.response.ListLinksResponse;
import edu.java.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class LinksController {
    private final LinkService linkService;

    @Operation(
        operationId = "linksDelete",
        summary = "Убрать отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )

    @DeleteMapping(value = "/links/{id}", produces = "application/json")
    public LinkResponse deleteLink(
        @PathVariable @Positive Long id,
        @RequestBody RemoveLinkRequest request
    ) {
        log.info("Delete link: {}, from chat: {}", request, id);
        Link removed = linkService.remove(id, URI.create(request.url()));
        return convertLink(removed);
    }

    @Operation(
        operationId = "linksGet",
        summary = "Получить все отслеживаемые ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )

    @GetMapping(value = "/links/{id}", produces = "application/json")
    public ListLinksResponse getLinks(@PathVariable @Positive Long id) {
        log.info("Get all links from chat: {}", id);
        List<Link> links = linkService.getAll(id);
        return new ListLinksResponse(
            links
                .stream()
                .map(this::convertLink)
                .toList(),
            links.size()
        );
    }

    @Operation(
        operationId = "linksPost",
        summary = "Добавить отслеживание ссылки",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping(value = "/links/{id}", produces = "application/json")

    public LinkResponse addLink(
        @PathVariable @Positive Long id,
        @RequestBody AddLinkRequest request
    ) {
        log.info("Adding link: {}, for chat: {}", request, id);
        Link saved = linkService.save(id, URI.create(request.url()));
        return convertLink(saved);
    }

    private LinkResponse convertLink(Link link) {
        return new LinkResponse(
            link.getId(),
            link.getUrl()
        );
    }
}
