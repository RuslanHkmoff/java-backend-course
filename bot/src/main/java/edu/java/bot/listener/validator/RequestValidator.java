package edu.java.bot.listener.validator;

import edu.java.models.request.LinkUpdateRequest;
import lombok.SneakyThrows;

public class RequestValidator {

    private static final String PROTOCOL = "https://";
    private static final String GITHUB_PREFIX = "github.com";
    private static final String SOF_PREFIX = "stackoverflow.com";
    private static final int SOF_PARAMETERS_LEN = 4;
    private static final int GITHUB_PARAMETERS_LEN = 3;

    private RequestValidator() {
    }

    @SneakyThrows
    public static void isValidRequestOrElseThrow(LinkUpdateRequest request) {
        if (request.id() < 0) {
            throw new InvalidMessageException();
        }
        String url = request.url();
        if (!url.startsWith(PROTOCOL)) {
            throw new InvalidMessageException();
        }
        String[] split = url.substring(PROTOCOL.length()).split("/");
        if (!isValidGithubLink(split) && !isValidSofLink(split)) {
            throw new InvalidMessageException();
        }
    }

    private static boolean isValidSofLink(String[] split) {
        if (SOF_PREFIX.equals(split[0])) {
            return "questions".equals(split[1]) && split.length == SOF_PARAMETERS_LEN;
        }
        return false;
    }

    private static boolean isValidGithubLink(String[] split) {
        if (!GITHUB_PREFIX.equals(split[0])) {
            return false;
        }
        return split.length == GITHUB_PARAMETERS_LEN;
    }
}

