package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TestCaseExpectedOutput(String value) {

    public static TestCaseExpectedOutput fromString(String expectedOutput) {
        if (expectedOutput == null) {
            throw new IllegalArgumentException(
                "Test case expected output cannot be null."
            );
        }

        return new TestCaseExpectedOutput(expectedOutput);
    }

    @Override
    public String toString() {
        return value;
    }
}