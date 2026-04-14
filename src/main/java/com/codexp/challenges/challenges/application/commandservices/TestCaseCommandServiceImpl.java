package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.domain.model.commands.AddTestCaseCommand;
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
}
