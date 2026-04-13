package com.codexp.challenges.challenges.interfaces.rest.requests;

public record CreateChallengeRequest(
    String title,
    String description,
    int difficulty,
    int rewardPoints
) {}
