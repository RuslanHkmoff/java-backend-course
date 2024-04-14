package edu.java.bot.controller;

import edu.java.bot.service.AlertService;
import edu.java.models.request.LinkUpdateRequest;
import edu.java.models.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Tag(name = "updates", description = "the updates API")
@RequiredArgsConstructor
public class UpdatesApiController {
    private final AlertService alertService;

    /**
     * POST /updates : Отправить обновление
     *
     * @param linkUpdate (required)
     *                   or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "updatesPost",
        summary = "Отправить обновление",
        responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @PostMapping("/updates")
    public void updatesPost(@RequestBody @Valid LinkUpdateRequest linkUpdate) {
        alertService.sendAlert(linkUpdate);
    }
}
