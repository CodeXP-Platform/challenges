package com.codexp.challenges.challenges.interfaces.rest.requests;

public record UpdateChallengeRequest(String title, String description, int difficulty, int rewardPoints, boolean isPublished) {
}
