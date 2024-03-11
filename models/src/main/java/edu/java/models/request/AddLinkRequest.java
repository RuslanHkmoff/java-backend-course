package edu.java.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddLinkRequest(
    @NotNull
    @NotBlank
    String url
) {
}
