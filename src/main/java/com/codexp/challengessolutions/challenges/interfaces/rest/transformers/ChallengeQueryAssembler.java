package com.codexp.challengessolutions.challenges.interfaces.rest.transformers;

import com.codexp.challengessolutions.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.ChallengeId;

public class ChallengeQueryAssembler {
    public static GetChallengeByIdQuery toGetChallengeByIdQuery(String challengeId) {
        return new GetChallengeByIdQuery(ChallengeId.fromString(challengeId));
    }
     public static GetChallengeByIdQuery toGetChallengeByIdQuery(ChallengeId challengeId) {
        return new GetChallengeByIdQuery(challengeId);
    }
}
