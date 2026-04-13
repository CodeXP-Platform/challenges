package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChallengeTitle(String value) {
    public static final int MIN_LENGTH = 6;
    public static final int MAX_LENGTH = 120;

    public static ChallengeTitle fromString(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Challenge title cannot be blank or empty.");
        }

        String normalized = title.trim();

        if (MIN_LENGTH > normalized.length() || normalized.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Challenge title must be between %d and %d characters long.", MIN_LENGTH, MAX_LENGTH)
            );
        }

        return new ChallengeTitle(normalized);
    }

    @Override
    public String toString() {
        return value;
    }
}
