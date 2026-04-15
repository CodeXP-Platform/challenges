package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

/**
 * Command to publish a challenge after validating templates and test cases.
 */
public record PublishChallengeCommand(
        ChallengeId challengeId,
        AuthorId authorId,
        UserRole authorRole
) {
}
