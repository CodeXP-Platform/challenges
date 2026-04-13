package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

/**
 * Command to create a draft challenge owned by a teacher.
 */
public record CreateChallengeCommand(
    ChallengeTitle title,
    ChallengeDescription description,
    AuthorId authorId,
    UserRole authorRole,
    ChallengeDifficulty difficulty,
    RewardPoints rewardPoints
) {}
