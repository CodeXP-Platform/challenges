package com.codexp.challenges.challenges.domain.model.queries;

import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeTitle;

public record GetChallengesByTitleQuery(ChallengeTitle challengeTitle) {
}
