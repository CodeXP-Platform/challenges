package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.PublishChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.services.ChallengeCommandService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

import org.springframework.stereotype.Service;

@Service
public class ChallengeCommandServiceImpl implements ChallengeCommandService {

    private final ChallengeRepository challengeRepository;

    public ChallengeCommandServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public ChallengeId handle(CreateChallengeCommand command) {

        if (command.authorRole().equals(UserRole.ROLE_STUDENT)) {
            throw new UnauthorizedActionException("Only teachers can create challenges");
        }

        var challengeId = ChallengeId.generate();

        var challenge = Challenge.create(
            challengeId,
            command.title(),
            command.description(),
            command.authorId(),
            command.difficulty(),
            command.rewardPoints()
        );

        challengeRepository.save(challenge);

        return challengeId;
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
