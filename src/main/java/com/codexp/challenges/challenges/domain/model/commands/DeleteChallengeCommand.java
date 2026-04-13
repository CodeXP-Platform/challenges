package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;

public record DeleteChallengeCommand(AuthorId authorId, ChallengeId challengeId) {
}
