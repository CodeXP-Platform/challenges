package com.codexp.challengessolutions.challenges.domain.model.commands;

import com.codexp.challengessolutions.challenges.domain.model.valueobjects.*;

/**
 * Command to create a draft challenge owned by a teacher.
 */
public record CreateChallengeCommand(
    ChallengeTitle title,
    ChallengeDescription description,
    AuthorId authorId,
    ChallengeDifficulty difficulty,
    RewardPoints rewardPoints
) {}
