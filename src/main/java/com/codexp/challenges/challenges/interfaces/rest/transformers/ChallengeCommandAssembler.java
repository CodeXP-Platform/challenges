package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import java.util.Optional;

public class ChallengeCommandAssembler {

    public static CreateChallengeCommand toCreateChallengeCommandFromRequest(
        CreateChallengeRequest request,
        UserId userId,
        UserRole userRole
    ) {
        return new CreateChallengeCommand(
            ChallengeTitle.fromString(request.title()),
            ChallengeDescription.fromString(request.description()),
            AuthorId.fromString(userId.value()),
            userRole,
            ChallengeDifficulty.fromInt(request.difficulty()),
            RewardPoints.fromInt(request.rewardPoints())
        );
    }

    public static UpdateChallengeCommand toUpdateChallengeCommandFromRequest(
        UpdateChallengeRequest request,
        String userId,
        UserRole userRole,
        String challengeId
    ) {
        return new UpdateChallengeCommand(
            ChallengeId.fromString(challengeId),
            AuthorId.fromString(userId),
            userRole,
            toOptionalTitle(request.title()),
            toOptionalDescription(request.description()),
            toOptionalDifficulty(request.difficulty()),
            toOptionalRewardPoints(request.rewardPoints())
        );
    }

    private static Optional<ChallengeTitle> toOptionalTitle(String title) {
        if (title == null) {
            return Optional.empty();
        }
        return Optional.of(ChallengeTitle.fromString(title));
    }

    private static Optional<ChallengeDescription> toOptionalDescription(
        String description
    ) {
        if (description == null) {
            return Optional.empty();
        }
        return Optional.of(ChallengeDescription.fromString(description));
    }

    private static Optional<ChallengeDifficulty> toOptionalDifficulty(
        Integer difficulty
    ) {
        if (difficulty == null) {
            return Optional.empty();
        }
        return Optional.of(ChallengeDifficulty.fromInt(difficulty));
    }

    private static Optional<RewardPoints> toOptionalRewardPoints(
        Integer rewardPoints
    ) {
        if (rewardPoints == null) {
            return Optional.empty();
        }
        return Optional.of(RewardPoints.fromInt(rewardPoints));
    }
}
