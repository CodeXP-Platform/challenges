package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.EntryFunctionName;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateCode;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;

/**
 * Command to create a language-specific template for a challenge.
 */
public record CreateCodeTemplateCommand(
    ChallengeId challengeId,
    EntryFunctionName entryFunctionName,
    TemplateLanguage language,
    TemplateCode templateCode
) {
}
