package com.codexp.challenges.challenges.interfaces.rest.requests;

public record CreateTestCaseRequest(
    String input,
    String expectedOutput,
    Boolean isHidden
) {}