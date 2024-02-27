package edu.java.response.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record QuestionResponse(
    @JsonProperty("owner") StackOverflowOwner owner,
    @JsonProperty("question_id") Long id,
    @JsonProperty("last_activity_date") OffsetDateTime lastActivity,

    @JsonProperty("answer_count") Integer answerCount
) {
}
