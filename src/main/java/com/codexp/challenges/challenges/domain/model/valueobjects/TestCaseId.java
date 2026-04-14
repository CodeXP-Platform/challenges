package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record TestCaseId(UUID value) {

    public static TestCaseId fromString(String testCaseId) {
        if (testCaseId == null || testCaseId.isBlank()) {
            throw new IllegalArgumentException(
                "Test case ID cannot be blank or empty."
            );
        }

        try {
            return new TestCaseId(UUID.fromString(testCaseId.trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid TestCase ID format. Must be a valid UUID.",
                e
            );
        }
    }

    public static TestCaseId generate() {
        return new TestCaseId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}