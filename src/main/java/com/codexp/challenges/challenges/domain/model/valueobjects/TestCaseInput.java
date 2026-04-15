package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TestCaseInput(String value) {

    public static TestCaseInput fromString(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(
                "Test case input cannot be blank or empty."
            );
        }

        return new TestCaseInput(input.trim());
    }

    @Override
    public String toString() {
        return value;
    }
}