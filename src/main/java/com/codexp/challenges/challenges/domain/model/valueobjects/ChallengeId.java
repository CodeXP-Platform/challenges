package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record ChallengeId(UUID value) {
    public static ChallengeId fromString(String challengeId) {
        if (challengeId == null || challengeId.isEmpty()) {
            throw new IllegalArgumentException("Challenge ID cannot be null or empty");
        }

        try {
            return new ChallengeId(UUID.fromString(challengeId.trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Author ID format. Must be a valid UUID.", e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static ChallengeId generate() {
        return new ChallengeId(UUID.randomUUID());
    }
}
