package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.AddTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseExpectedOutput;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseHidden;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseInput;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateTestCaseRequest;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

public class TestCaseCommandAssembler {

    public static AddTestCaseCommand toAddTestCaseCommandFromRequest(
        CreateTestCaseRequest request,
        String challengeId,
        UserId userId,
        UserRole userRole
    ) {
        return new AddTestCaseCommand(
            AuthorId.fromUserId(userId),
            userRole,
            ChallengeId.fromString(challengeId),
            TestCaseInput.fromString(request.input()),
            TestCaseExpectedOutput.fromString(request.expectedOutput()),
            TestCaseHidden.fromBoolean(request.isHidden())
        );
    }
}