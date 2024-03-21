package edu.java.service.updates;

import java.net.URI;

public record LinkUpdateDto(
    URI url,
    String description
) {
}
