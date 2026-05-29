package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.queries.ExistsChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengesByTitleQuery;
import com.codexp.challenges.challenges.domain.model.queries.FindChallengesQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChallengeQueryService {
    Page<Challenge> handle(GetAllChallengesQuery query, Pageable pageable);

    Challenge handle(GetChallengeByIdQuery query);

    Page<Challenge> handle(GetChallengesByTitleQuery query, Pageable pageable);

    Page<Challenge> handle(FindChallengesQuery query, Pageable pageable);

    boolean handle(ExistsChallengeByIdQuery query);
}
