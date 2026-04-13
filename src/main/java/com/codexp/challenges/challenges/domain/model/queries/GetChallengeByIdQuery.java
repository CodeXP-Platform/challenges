package com.codexp.challenges.challenges.domain.model.queries;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;

public record GetChallengeByIdQuery(ChallengeId challengeId, AuthorId requester) {
}
