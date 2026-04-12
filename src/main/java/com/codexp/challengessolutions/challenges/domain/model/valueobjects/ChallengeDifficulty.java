package com.codexp.challengessolutions.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChallengeDifficulty(int value) {
    public static int MIN_DIFFICULTY = 1;
    public static int MAX_DIFFICULTY = 10;

    public static ChallengeDifficulty fromInt(int value) {
        if (value < MIN_DIFFICULTY || value > MAX_DIFFICULTY) {
            throw new IllegalArgumentException(
                "Challenge difficulty must be between " +
                    MIN_DIFFICULTY +
                    " and " +
                    MAX_DIFFICULTY +
                    " inclusive."
            );
        }
        return new ChallengeDifficulty(value);
    }
}
