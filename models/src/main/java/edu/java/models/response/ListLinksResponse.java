package edu.java.models.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public record ListLinksResponse(
    @Valid
    List<@Valid LinkResponse> links,
    @PositiveOrZero
    Integer size
) {
}

