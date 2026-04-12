package com.codexp.challengessolutions.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record AuthorId(UUID value) {
}
