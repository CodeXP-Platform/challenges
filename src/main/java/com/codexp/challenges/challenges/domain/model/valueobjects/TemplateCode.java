package com.codexp.challenges.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TemplateCode(String value) {

    public static TemplateCode fromString(String templateCode) {
        if (templateCode == null || templateCode.isBlank()) {
            throw new IllegalArgumentException(
                "Template code cannot be blank or empty."
            );
        }

        return new TemplateCode(templateCode);
    }

    @Override
    public String toString() {
        return value;
    }
}