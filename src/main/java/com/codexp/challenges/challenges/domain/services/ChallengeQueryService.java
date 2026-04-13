package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.queries.ExistsChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengesByTitleQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application contract for challenge read use cases.
 */
public interface ChallengeQueryService {
    List<Challenge> handle(GetAllChallengesQuery query);

    Challenge handle(GetChallengeByIdQuery query);

    List<Challenge> handle(GetChallengesByTitleQuery query);

    boolean handle(ExistsChallengeByIdQuery query);
}
