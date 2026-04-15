package com.codexp.challenges.challenges.interfaces.rest.requests;

public record CreateSolutionRequest(
    String language,
    String sourceCode
) {
}
