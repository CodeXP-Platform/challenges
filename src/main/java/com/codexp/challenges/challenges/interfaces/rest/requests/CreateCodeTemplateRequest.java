package com.codexp.challenges.challenges.interfaces.rest.requests;

public record CreateCodeTemplateRequest(
    String entryFunctionName,
    String language,
    String templateCode
) {}