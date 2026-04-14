package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record CodeTemplateId(UUID value) {

    public static CodeTemplateId fromString(String codeTemplateId) {
        if (codeTemplateId == null || codeTemplateId.isBlank()) {
            throw new IllegalArgumentException(
                "Code template ID cannot be blank or empty."
            );
        }

        try {
            return new CodeTemplateId(UUID.fromString(codeTemplateId.trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid CodeTemplate ID format. Must be a valid UUID.",
                e
            );
        }
    }

    public static CodeTemplateId generate() {
        return new CodeTemplateId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}