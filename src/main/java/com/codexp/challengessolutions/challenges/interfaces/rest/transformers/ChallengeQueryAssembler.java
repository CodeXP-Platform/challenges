package com.codexp.challengessolutions.challenges.interfaces.rest.transformers;

import com.codexp.challengessolutions.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challengessolutions.shared.domain.model.valueobjects.UserId;

public class ChallengeQueryAssembler {
    public static GetChallengeByIdQuery toGetChallengeByIdQuery(String challengeId, UserId requester) {
        return new GetChallengeByIdQuery(ChallengeId.fromString(challengeId), AuthorId.fromUserId(requester));
    }
     public static GetChallengeByIdQuery toGetChallengeByIdQuery(ChallengeId challengeId, UserId requester) {
        return new GetChallengeByIdQuery(challengeId, AuthorId.fromUserId(requester));
    }
}
