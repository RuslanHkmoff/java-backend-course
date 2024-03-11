package edu.java.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record LinkUpdateRequest(
    @Positive
    Long id,
    @NotNull
    @NotBlank
    String url,
    String description,
    List<Long> thChatIds
) {
}
