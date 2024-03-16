package edu.java.util;

public class LinkParser {
    private static final int GITHUB_OFFSET = 19;
    private static final int STACKOVERFLOW_OFFSET = 26;

    private LinkParser() {
    }

    public static GithubRequestParameters extractGithubData(String url) {
        String[] parsedUrl = url.substring(GITHUB_OFFSET).split("/");
        return new GithubRequestParameters(parsedUrl[0], parsedUrl[1]);
    }

    public static Long extractSofData(String url) {
        String[] parsedUrl = url.substring(STACKOVERFLOW_OFFSET).split("/");
        return Long.valueOf(parsedUrl[1]);
    }
}
