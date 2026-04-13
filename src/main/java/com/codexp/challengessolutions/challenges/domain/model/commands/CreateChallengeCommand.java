package com.codexp.challengessolutions.challenges.domain.model.commands;

import com.codexp.challengessolutions.challenges.domain.model.valueobjects.*;
import com.codexp.challengessolutions.shared.domain.model.valueobjects.UserRole;

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
