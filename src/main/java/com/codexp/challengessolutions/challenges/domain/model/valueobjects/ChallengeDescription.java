package com.codexp.challengessolutions.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChallengeDescription(String value) {
    public static final int MIN_LENGTH = 6;
    public static final int MAX_LENGTH = 1500;

    public static ChallengeDescription fromString(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Challenge description cannot be blank or empty.");
        }

        String normalized = description.trim();

        if (MIN_LENGTH > normalized.length() || normalized.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Challenge description must be between %d and %d characters long.", MIN_LENGTH, MAX_LENGTH)
            );
        }

        return new ChallengeDescription(normalized);
    }

    @Override
    public String toString() {
        return value;
    }
}
