package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

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
        String challengeId
    ) {
        return new UpdateChallengeCommand(
            ChallengeId.fromString(challengeId),
            ChallengeTitle.fromString(request.title()),
            ChallengeDescription.fromString(request.description()),
            AuthorId.fromString(userId),
            ChallengeDifficulty.fromInt(request.difficulty()),
            RewardPoints.fromInt(request.rewardPoints())
        );
    }
}
