package com.codexp.challengessolutions.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ChallengeDifficulty(int value) {
    public static int MIN_DIFFICULTY = 1;
    public static int MAX_DIFFICULTY = 10;
}
