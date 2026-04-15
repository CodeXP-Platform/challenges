package com.codexp.challenges.challenges.interfaces.rest.requests;

public record UpdateCodeTemplateRequest(
    String entryFunctionName,
    String language,
    String templateCode
) {}
