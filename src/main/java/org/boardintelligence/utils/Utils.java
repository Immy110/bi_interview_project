package org.boardintelligence.utils;

public class Utils {

    private static final String baseUrl = System.getenv()
            .getOrDefault("GITLAB_ENVIRONMENT_URL", "https://www.boardintelligence.com/")
            .trim()
            .replaceFirst("/$", "")
            ;

    public static String getBaseUrl() {
        return baseUrl;
    }
}
