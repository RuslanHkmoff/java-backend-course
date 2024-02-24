package edu.java.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubOwner(
    @JsonProperty("login") String login,
    @JsonProperty("id") Long id) {
}
