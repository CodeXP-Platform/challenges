package com.codexp.challenges.challenges.application.queryservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.queries.ExistsChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengesByTitleQuery;
import com.codexp.challenges.challenges.domain.services.ChallengeQueryService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChallengeQueryServiceImpl implements ChallengeQueryService {

    private final ChallengeRepository challengeRepository;

    public ChallengeQueryServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public Page<Challenge> handle(
            GetAllChallengesQuery query,
            Pageable pageable
    ) {
        return challengeRepository.findAllByIsPublishedTrue(true, pageable);
    }

    @Override
    public Page<Challenge> handle(
        GetChallengesByTitleQuery query,
        Pageable pageable
    ) {
        var normalizedTitle = query.challengeTitle().value().trim();
        return challengeRepository
            .findByTitle_ValueContainingIgnoreCase(normalizedTitle, pageable);
    }

    @Override
    public boolean handle(ExistsChallengeByIdQuery query) {
        return challengeRepository.existsById(query.challengeId());
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

        throw new UnauthorizedActionException("This challenge is not published or your are not the owner.");
    }
}
