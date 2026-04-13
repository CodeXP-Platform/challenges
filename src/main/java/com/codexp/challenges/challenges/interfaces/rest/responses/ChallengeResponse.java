package com.codexp.challenges.challenges.interfaces.rest.responses;

import java.time.Instant;

public record ChallengeResponse(String challengeId, String title, String description, String authorId, int difficulty, int rewardPoints, boolean isPublished, Instant updatedAt, Instant createdAt) {
}
