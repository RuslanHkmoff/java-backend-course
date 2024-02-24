package edu.java.response.stackoverflow;

import java.util.List;

public record StackOverflowResponse(List<QuestionResponse> items) {
}
