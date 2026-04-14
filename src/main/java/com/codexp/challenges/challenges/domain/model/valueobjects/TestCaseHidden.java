package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TestCaseHidden(Boolean value) {

    public static TestCaseHidden fromBoolean(Boolean hidden) {
        if (hidden == null) {
            throw new IllegalArgumentException(
                "Test case hidden flag cannot be null."
            );
        }

        return new TestCaseHidden(hidden);
    }
}