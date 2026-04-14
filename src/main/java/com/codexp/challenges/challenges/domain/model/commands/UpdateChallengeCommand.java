package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import java.util.Optional;

/**
 * Command to update an existing challenge owned by a teacher.
 */
public record UpdateChallengeCommand(
        ChallengeId challengeId,
        AuthorId authorId,
        Optional<ChallengeTitle> title,
        Optional<ChallengeDescription> description,
        Optional<ChallengeDifficulty> difficulty,
        Optional<RewardPoints> rewardPoints
) {
    public boolean hasChanges() {
        return title.isPresent()
                || description.isPresent()
                || difficulty.isPresent()
                || rewardPoints.isPresent();
    }
}
