package com.codexp.challengessolutions.challenges.interfaces.rest.responses;

import java.util.Date;

public record ChallengeResponse(String id, String title, String description, String authorId, int difficulty, int rewardPoints, boolean isPublished, Date updatedAt, Date createdAt) {
}
