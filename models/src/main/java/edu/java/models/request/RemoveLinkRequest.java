package edu.java.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RemoveLinkRequest(
    @NotNull
    @NotBlank
    String url
) {
}
