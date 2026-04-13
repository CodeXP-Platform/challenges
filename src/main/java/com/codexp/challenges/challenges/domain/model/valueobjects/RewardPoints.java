package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record RewardPoints(int value) {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 100;

    public static RewardPoints fromInt(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(
                "Reward points must be between " +
                    MIN_VALUE +
                    " and " +
                    MAX_VALUE
            );
        }

        return new RewardPoints(value);
    }
}
