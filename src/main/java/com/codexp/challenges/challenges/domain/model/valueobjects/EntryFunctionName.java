package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record EntryFunctionName(String value) {
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 100;

    public static EntryFunctionName fromString(String entryFunctionName) {
        if (entryFunctionName == null || entryFunctionName.isBlank()) {
            throw new IllegalArgumentException(" cannot be blank or empty.");
        }

        var normalized = entryFunctionName.trim();

        if (normalized.contains(" ")) {
            throw new IllegalArgumentException(
                "Entry function name cannot contain spaces."
            );
        }

        if (
            normalized.length() < MIN_LENGTH || normalized.length() > MAX_LENGTH
        ) {
            throw new IllegalArgumentException(
                "Entry function name must be between " +
                    MIN_LENGTH +
                    " and " +
                    MAX_LENGTH +
                    " characters."
            );
        }

        return new EntryFunctionName(normalized);
    }
}
