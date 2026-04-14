package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengesByTitleQuery;
import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeTitle;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;

public class ChallengeQueryAssembler {
    public static GetChallengeByIdQuery toGetChallengeByIdQuery(
        String challengeId,
        UserId requester
    ) {
        return new GetChallengeByIdQuery(
            ChallengeId.fromString(challengeId),
            AuthorId.fromUserId(requester)
        );
    }

    public static GetChallengeByIdQuery toGetChallengeByIdQuery(
        ChallengeId challengeId,
        UserId requester
    ) {
        return new GetChallengeByIdQuery(challengeId, AuthorId.fromUserId(requester));
    }

    public static GetAllChallengesQuery toGetAllChallengesQuery() {
        return new GetAllChallengesQuery();
    }

    public static GetChallengesByTitleQuery toGetChallengesByTitleQuery(
        String title
    ) {
        return new GetChallengesByTitleQuery(ChallengeTitle.fromString(title));
    }
}
