package com.codexp.challengessolutions.challenges.domain.model.commands;

/**
 * Shared command contract for language-agnostic challenge test cases.
 */
public record AddTestCaseCommand(
        String input,
        String expectedOutput,
        Boolean hidden
) {
}
