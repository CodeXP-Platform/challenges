package com.codexp.challenges.challenges.application.queryservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.queries.ExistsChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetAllChallengesQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengeByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetChallengesByTitleQuery;
import com.codexp.challenges.challenges.domain.model.queries.FindChallengesQuery;
import com.codexp.challenges.challenges.domain.services.ChallengeQueryService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return challengeRepository.findPublishedByFilters(
            query.title(), null, null, null, withoutSort(pageable)
        );
    }

    @Override
    public Page<Challenge> handle(FindChallengesQuery query, Pageable pageable) {
        return challengeRepository.findPublishedByFilters(
            query.title(),
            query.minDifficulty(),
            query.maxDifficulty(),
            query.language(),
            withoutSort(pageable)
        );
    }

    // The native filter query carries its own ORDER BY (created_at). Strip the
    // Pageable sort so Spring doesn't append entity property names (e.g. "createdAt")
    // as raw column names to the native SQL.
    private static Pageable withoutSort(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
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
