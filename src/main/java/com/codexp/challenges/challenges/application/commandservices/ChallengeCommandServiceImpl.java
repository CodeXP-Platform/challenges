package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteChallengeCommand;
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

        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
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
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException("Only teachers can update challenges");
        }

        if (!command.hasChanges()) {
            throw new IllegalArgumentException(
                "At least one challenge field must be provided for update"
            );
        }

        var challenge = challengeRepository
            .findById(command.challengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can update it"
            );
        }

        challenge.updatePartially(
            command.title(),
            command.description(),
            command.difficulty(),
            command.rewardPoints()
        );

        return challengeRepository.save(challenge);
    }

    @Override
    public Challenge handle(PublishChallengeCommand command) {
        return null;
    }

    @Override
    public void handle(DeleteChallengeCommand command) {
        var challenge = challengeRepository
            .findById(command.challengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can delete it"
            );
        }
        // TODO: Add a validation to allow ROLE_ADMIN to delete any challenge, regardless of ownership

        challengeRepository.delete(challenge);
    }
}
