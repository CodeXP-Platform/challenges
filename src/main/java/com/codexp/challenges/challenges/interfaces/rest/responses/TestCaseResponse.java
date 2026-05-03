package com.codexp.challenges.challenges.interfaces.rest.responses;

import java.time.Instant;

public record TestCaseResponse(
    String testCaseId,
    String codeTemplateId,
    String input,
    String expectedOutput,
    Boolean isHidden,
    Instant updatedAt,
    Instant createdAt
) {}