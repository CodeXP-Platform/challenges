package com.codexp.challengessolutions.challenges.domain.model.commands;

/**
 * Shared command contract for challenge code templates.
 */
public record CreateCodeTemplateCommand(
        String language,
        String templateCode
) {
}
