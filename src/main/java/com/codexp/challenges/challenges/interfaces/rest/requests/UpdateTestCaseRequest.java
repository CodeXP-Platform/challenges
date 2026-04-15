package com.codexp.challenges.challenges.interfaces.rest.requests;

public record UpdateTestCaseRequest(
    String input,
    String expectedOutput,
    Boolean isHidden
) {}
