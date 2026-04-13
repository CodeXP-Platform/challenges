package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.PublishChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;

/**
 * Application contract for state-changing challenge use cases.
 */
public interface ChallengeCommandService {
    ChallengeId handle(CreateChallengeCommand command);

    Challenge handle(UpdateChallengeCommand command);

    Challenge handle(PublishChallengeCommand command);
}
