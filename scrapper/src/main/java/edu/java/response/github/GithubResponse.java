package edu.java.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GithubResponse(
    @JsonProperty("owner") GithubOwner owner,
    @JsonProperty("updated_at") OffsetDateTime updateAt,
    @JsonProperty("pushed_at") OffsetDateTime pushedAt,
    @JsonProperty("open_issues_count") Integer openIssuesCount
) {
}
