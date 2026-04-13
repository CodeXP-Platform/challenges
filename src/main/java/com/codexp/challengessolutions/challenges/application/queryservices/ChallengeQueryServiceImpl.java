package com.codexp.challengessolutions.challenges.application.queryservices;

import com.codexp.challengessolutions.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challengessolutions.challenges.domain.model.Challenge;
import com.codexp.challengessolutions.challenges.domain.model.queries.ExistsChallengeByIdQuery;
import com.codexp.challengessolutions.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challengessolutions.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challengessolutions.challenges.domain.model.queries.GetChallengesByTitleQuery;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challengessolutions.challenges.domain.services.ChallengeQueryService;
import com.codexp.challengessolutions.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challengessolutions.shared.domain.exceptions.UnauthorizedActionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChallengeQueryServiceImpl implements ChallengeQueryService {

    private final ChallengeRepository challengeRepository;

    public ChallengeQueryServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

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
    public Challenge handle(GetChallengeByIdQuery query) {
        var result = challengeRepository.findById(query.challengeId());

        if (result.isEmpty()) {
            throw new ChallengeNotFoundException();
        }

        var challenge = result.get();

        if (challenge.isPublished()) {
            return challenge;
        }

        if (challenge.isOwnedBy(query.requester())) {
            return challenge;
        }

        throw new UnauthorizedActionException("Cannot access to this resource");
    }
}
