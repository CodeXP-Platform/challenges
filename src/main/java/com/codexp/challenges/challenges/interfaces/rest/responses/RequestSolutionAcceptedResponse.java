package com.codexp.challenges.challenges.interfaces.rest.responses;

public record RequestSolutionAcceptedResponse(
    String challengeId,
    String language,
    String status
) {}
