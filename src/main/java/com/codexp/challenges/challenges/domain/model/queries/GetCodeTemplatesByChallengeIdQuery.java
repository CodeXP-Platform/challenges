package com.codexp.challenges.challenges.domain.model.queries;

import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;

public record GetCodeTemplatesByChallengeIdQuery(ChallengeId challengeId) {
}