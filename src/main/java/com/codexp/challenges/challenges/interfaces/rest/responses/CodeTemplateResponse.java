package com.codexp.challenges.challenges.interfaces.rest.responses;

import java.time.Instant;

public record CodeTemplateResponse(
    String codeTemplateId,
    String challengeId,
    String entryFunctionName,
    String language,
    String templateCode,
    Instant updatedAt,
    Instant createdAt
) {}