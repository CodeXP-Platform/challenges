package com.codexp.challengessolutions.challenges.application.queryservices;

import com.codexp.challengessolutions.challenges.domain.model.Challenge;
import com.codexp.challengessolutions.challenges.domain.model.queries.ExistsChallengeByIdQuery;
import com.codexp.challengessolutions.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challengessolutions.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challengessolutions.challenges.domain.model.queries.GetChallengesByTitleQuery;
import com.codexp.challengessolutions.challenges.domain.services.ChallengeQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeQueryServiceImpl implements ChallengeQueryService {
    @Override
    public List<Challenge> handle(GetAllChallengesQuery query) {
        return List.of();
    }

    @Override
    public List<Challenge> handle(GetChallengesByTitleQuery query) {
        return List.of();
    }

    @Override
    public boolean handle(ExistsChallengeByIdQuery query) {
        return false;
    }

    @Override
    public Optional<Challenge> handle(GetChallengeByIdQuery query) {
        return Optional.empty();
    }
}
