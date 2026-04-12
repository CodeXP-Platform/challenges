package com.codexp.challengessolutions.challenges.domain.model.commands;

import com.codexp.challengessolutions.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.ChallengeId;

public record DeleteChallengeCommand(AuthorId authorId, ChallengeId challengeId) {
}
