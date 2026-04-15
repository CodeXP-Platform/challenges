package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.exceptions.TestCaseNotFoundException;
import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.domain.model.commands.AddTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;
import com.codexp.challenges.challenges.domain.services.TestCaseCommandService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.TestCaseRepository;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import org.springframework.stereotype.Service;

@Service
public class TestCaseCommandServiceImpl implements TestCaseCommandService {

    private final TestCaseRepository testCaseRepository;
    private final ChallengeRepository challengeRepository;

    public TestCaseCommandServiceImpl(
        TestCaseRepository testCaseRepository,
        ChallengeRepository challengeRepository
    ) {
        this.testCaseRepository = testCaseRepository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public TestCaseId handle(AddTestCaseCommand command) {
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can add challenge test cases"
            );
        }

        var challenge = challengeRepository
            .findById(command.challengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can add test cases"
            );
        }

        var testCaseId = TestCaseId.generate();

        var testCase = TestCase.create(
            testCaseId,
            command.challengeId(),
            command.input(),
            command.expectedOutput(),
            command.isHidden()
        );

        testCaseRepository.save(testCase);

        return testCaseId;
    }

    @Override
    public TestCase handle(UpdateTestCaseCommand command) {
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can update challenge test cases"
            );
        }

        if (!command.hasChanges()) {
            throw new IllegalArgumentException(
                "At least one test case field must be provided for update"
            );
        }

        var testCase = testCaseRepository
            .findById(command.testCaseId())
            .orElseThrow(TestCaseNotFoundException::new);

        var challenge = challengeRepository
            .findById(testCase.getChallengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can update test cases"
            );
        }

        testCase.updatePartially(
            command.input(),
            command.expectedOutput(),
            command.isHidden()
        );

        return testCaseRepository.save(testCase);
    }

    @Override
    public void handle(DeleteTestCaseCommand command) {
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can delete challenge test cases"
            );
        }

        var testCase = testCaseRepository
            .findById(command.testCaseId())
            .orElseThrow(TestCaseNotFoundException::new);

        var challenge = challengeRepository
            .findById(testCase.getChallengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can delete test cases"
            );
        }

        testCaseRepository.delete(testCase);
    }
}
