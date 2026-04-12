package com.codexp.challengessolutions.challenges.domain.services;

import com.codexp.challengessolutions.challenges.domain.model.Challenge;
import com.codexp.challengessolutions.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.model.commands.PublishChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.model.commands.UpdateChallengeCommand;

/**
 * Application contract for state-changing challenge use cases.
 */
public interface ChallengeCommandService {
    Challenge handle(CreateChallengeCommand command);

    Challenge handle(UpdateChallengeCommand command);

    Challenge handle(PublishChallengeCommand command);
}
