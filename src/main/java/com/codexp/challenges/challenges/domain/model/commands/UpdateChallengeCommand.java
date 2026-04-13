package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.*;

/**
 * Command to update an existing challenge owned by a teacher.
 */
public record UpdateChallengeCommand(
        ChallengeId challengeId,
        ChallengeTitle title,
        ChallengeDescription description,
        AuthorId authorId,
        ChallengeDifficulty difficulty,
        RewardPoints rewardPoints
) {
}
