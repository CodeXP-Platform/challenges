package com.codexp.challengessolutions.challenges.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record RewardPoints(int value) {
}
