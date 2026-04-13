package com.codexp.challengessolutions.challenges.domain.model.queries;

import com.codexp.challengessolutions.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.ChallengeId;

public record GetChallengeByIdQuery(ChallengeId challengeId, AuthorId requester) {
}
