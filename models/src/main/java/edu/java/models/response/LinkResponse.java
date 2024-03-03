package edu.java.models.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.net.URI;

public record LinkResponse(
    @Positive
    Long id,
    @NotNull
    @NotBlank
    URI url
) {
}
