package com.codexp.challenges.challenges.interfaces.rest.responses;

import java.util.List;

public record SubmitChallengeContextResponse(
    String templateCode,
    String language,
    String entryFunctionName,
    List<SubmitTestCaseResponse> testCases
) {
    public record SubmitTestCaseResponse(
        String testId,
        String input,
        String expectedOutput,
        Boolean isHidden
    ) {}
}
