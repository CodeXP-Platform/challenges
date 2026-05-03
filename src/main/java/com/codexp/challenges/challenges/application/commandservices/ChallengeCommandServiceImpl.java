package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.commands.CreateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.PublishChallengeCommand;
import com.codexp.challenges.challenges.domain.model.commands.RequestSolutionCreationCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateChallengeCommand;
import com.codexp.challenges.challenges.domain.model.events.SolutionRequestedEvent;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.services.ChallengeCommandService;
import com.codexp.challenges.challenges.domain.services.SolutionRequestedEventPublisher;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.CodeTemplateRepository;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.TestCaseRepository;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChallengeCommandServiceImpl implements ChallengeCommandService {

    private final ChallengeRepository challengeRepository;
    private final TestCaseRepository testCaseRepository;
    private final CodeTemplateRepository codeTemplateRepository;
    private final SolutionRequestedEventPublisher solutionRequestedEventPublisher;

    public ChallengeCommandServiceImpl(
        ChallengeRepository challengeRepository,
        TestCaseRepository testCaseRepository,
        CodeTemplateRepository codeTemplateRepository,
        SolutionRequestedEventPublisher solutionRequestedEventPublisher
    ) {
        this.challengeRepository = challengeRepository;
        this.testCaseRepository = testCaseRepository;
        this.codeTemplateRepository = codeTemplateRepository;
        this.solutionRequestedEventPublisher = solutionRequestedEventPublisher;
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
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException("Only teachers can publish challenges");
        }

        var challenge = challengeRepository
            .findById(command.challengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can publish it"
            );
        }

        var codeTemplates = codeTemplateRepository.findByChallengeId(
            command.challengeId()
        );
        if (codeTemplates.isEmpty()) {
            throw new IllegalArgumentException(
                "Challenge must have at least one code template before publishing"
            );
        }

        var allEntryFunctionNamesValid = codeTemplates
            .stream()
            .allMatch(template ->
                template.getEntryFunctionName() != null &&
                template.getEntryFunctionName().value() != null &&
                !template.getEntryFunctionName().value().isBlank()
            );
        if (!allEntryFunctionNamesValid) {
            throw new IllegalArgumentException(
                "All code templates must define a valid entry function name before publishing"
            );
        }

        boolean hasTestCases = false;
        for (var template : codeTemplates) {
            if (!testCaseRepository.findByCodeTemplateId(template.getId()).isEmpty()) {
                hasTestCases = true;
                break;
            }
        }
        if (!hasTestCases) {
            throw new IllegalArgumentException(
                "Challenge must have at least one test case before publishing"
            );
        }

        challenge.publish();
        return challengeRepository.save(challenge);
    }

    @Override
    @Transactional
    public void handle(RequestSolutionCreationCommand command) {
        if (
            !command.authorRole().equals(UserRole.ROLE_STUDENT) &&
            !command.authorRole().equals(UserRole.ROLE_ADMIN)
        ) {
            throw new UnauthorizedActionException(
                "Only students or admins can request solutions"
            );
        }

        var challenge = challengeRepository
            .findById(command.challengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isPublished()) {
            throw new IllegalArgumentException(
                "Challenge must be published before requesting a solution"
            );
        }

        var codeTemplate = codeTemplateRepository
            .findFirstByChallengeIdAndLanguage(
                command.challengeId(),
                command.language()
            )
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "No code template found for requested language"
                )
            );

        var event = SolutionRequestedEvent.create(
            command.challengeId().toString(),
            command.authorId().toString(),
            codeTemplate.getLanguage().toString(),
            codeTemplate.getTemplateCode().toString()
        );

        solutionRequestedEventPublisher.publish(event);
    }

    @Override
    @Transactional
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

        var codeTemplatesForDelete = codeTemplateRepository.findByChallengeId(command.challengeId());
        for (var template : codeTemplatesForDelete) {
            testCaseRepository.deleteByCodeTemplateId(template.getId());
        }
        codeTemplateRepository.deleteByChallengeId(command.challengeId());
        challengeRepository.delete(challenge);
    }
}
