package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.interfaces.rest.responses.ChallengeResponse;

public class ChallengeAssembler {

    public static ChallengeResponse toResponseFromEntity(Challenge entity) {
        return new ChallengeResponse(
            entity.getId().toString(),
            entity.getTitle().toString(),
            entity.getDescription().toString(),
            entity.getAuthorId().toString(),
            entity.getDifficulty().value(),
            entity.getRewardPoints().value(),
            entity.getIsPublished(),
            entity.getUpdatedAt(),
            entity.getCreatedAt()
        );
    }
}
