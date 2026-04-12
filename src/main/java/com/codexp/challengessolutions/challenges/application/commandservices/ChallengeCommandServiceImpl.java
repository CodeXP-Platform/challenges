package com.codexp.challengessolutions.challenges.application.commandservices;

import com.codexp.challengessolutions.challenges.domain.model.Challenge;
import com.codexp.challengessolutions.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.model.commands.PublishChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challengessolutions.challenges.domain.services.ChallengeCommandService;
import org.springframework.stereotype.Service;

@Service
public class ChallengeCommandServiceImpl implements ChallengeCommandService {

    @Override
    public Challenge handle(CreateChallengeCommand command) {
        return null;
    }

    @Override
    public Challenge handle(UpdateChallengeCommand command) {
        return null;
    }

    @Override
    public Challenge handle(PublishChallengeCommand command) {
        return null;
    }
}
