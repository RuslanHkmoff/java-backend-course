package edu.java.response.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowOwner(
    @JsonProperty("display_name") String displayName,
    @JsonProperty("user_id") Long id
) {
}
