package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.PublishChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.RequestSolutionCreationCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateSolutionRequest;
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

    public static DeleteChallengeCommand toDeleteChallengeCommandFromRequest(
        String challengeId,
        String userId
    ) {
        return new DeleteChallengeCommand(
            AuthorId.fromString(userId),
            ChallengeId.fromString(challengeId)
        );
    }

    public static PublishChallengeCommand toPublishChallengeCommandFromRequest(
        String challengeId,
        String userId,
        UserRole userRole
    ) {
        return new PublishChallengeCommand(
            ChallengeId.fromString(challengeId),
            AuthorId.fromString(userId),
            userRole
        );
    }

    public static RequestSolutionCreationCommand toRequestSolutionCreationCommand(
        String challengeId,
        String requesterId,
        UserRole requesterRole,
        CreateSolutionRequest request
    ) {
        return new RequestSolutionCreationCommand(
            ChallengeId.fromString(challengeId),
            AuthorId.fromString(requesterId),
            requesterRole,
            TemplateLanguage.fromString(request.language())
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
