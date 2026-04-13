package com.codexp.challengessolutions.challenges.interfaces.rest.responses;

import java.time.Instant;

public record ChallengeResponse(String id, String title, String description, String authorId, int difficulty, int rewardPoints, boolean isPublished, Instant updatedAt, Instant createdAt) {
}
