package com.codexp.challenges.challenges.domain.model.queries;

public record FindChallengesQuery(String title, Integer minDifficulty, Integer maxDifficulty, String language) {

    public static final int EASY_MIN = 1;
    public static final int EASY_MAX = 3;
    public static final int MEDIUM_MIN = 4;
    public static final int MEDIUM_MAX = 7;
    public static final int HARD_MIN = 8;
    public static final int HARD_MAX = 10;

    public static FindChallengesQuery of(String title, String difficulty, String language) {
        Integer min = null;
        Integer max = null;
        if (difficulty != null && !difficulty.isBlank()) {
            switch (difficulty.trim().toLowerCase()) {
                case "easy"   -> { min = EASY_MIN;   max = EASY_MAX; }
                case "medium" -> { min = MEDIUM_MIN; max = MEDIUM_MAX; }
                case "hard"   -> { min = HARD_MIN;   max = HARD_MAX; }
            }
        }
        String normalizedTitle    = (title    != null && !title.isBlank())    ? title.trim()              : null;
        String normalizedLanguage = (language != null && !language.isBlank()) ? language.trim().toLowerCase() : null;
        return new FindChallengesQuery(normalizedTitle, min, max, normalizedLanguage);
    }
}
