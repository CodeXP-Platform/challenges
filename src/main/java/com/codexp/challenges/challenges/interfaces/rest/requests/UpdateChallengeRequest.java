package com.codexp.challenges.challenges.interfaces.rest.requests;

public record UpdateChallengeRequest(
	String title,
	String description,
	Integer difficulty,
	Integer rewardPoints,
	Boolean isPublished
) {}
