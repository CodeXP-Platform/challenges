package com.codexp.challengessolutions.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record AuthorId(UUID value) {

    public static AuthorId fromString(String authorId) {
        if (authorId == null || authorId.isBlank()) {
            throw new IllegalArgumentException("Author ID cannot be blank or empty.");
        }

        try {
            return new AuthorId(UUID.fromString(authorId.trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Author ID format. Must be a valid UUID.", e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
