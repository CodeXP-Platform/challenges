package com.codexp.challengessolutions.challenges.interfaces.rest.transformers;

import com.codexp.challengessolutions.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.*;
import com.codexp.challengessolutions.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challengessolutions.challenges.interfaces.rest.requests.UpdateChallengeRequest;

public class ChallengeCommandAssembler {

    public static CreateChallengeCommand toCreateChallengeCommandFromRequest(
        CreateChallengeRequest request,
        String userId
    ) {
        return new CreateChallengeCommand(
            ChallengeTitle.fromString(request.title()),
            ChallengeDescription.fromString(request.description()),
            AuthorId.fromString(userId),
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
