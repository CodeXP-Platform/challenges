package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.AddTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseExpectedOutput;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseHidden;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseInput;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateTestCaseRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateTestCaseRequest;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import java.util.Optional;

public class TestCaseCommandAssembler {

    public static AddTestCaseCommand toAddTestCaseCommandFromRequest(
        CreateTestCaseRequest request,
        String codeTemplateId,
        UserId userId,
        UserRole userRole
    ) {
        return new AddTestCaseCommand(
            AuthorId.fromUserId(userId),
            userRole,
            CodeTemplateId.fromString(codeTemplateId),
            TestCaseInput.fromString(request.input()),
            TestCaseExpectedOutput.fromString(request.expectedOutput()),
            TestCaseHidden.fromBoolean(request.isHidden())
        );
    }

    public static UpdateTestCaseCommand toUpdateTestCaseCommandFromRequest(
        UpdateTestCaseRequest request,
        String testCaseId,
        UserId userId,
        UserRole userRole
    ) {
        return new UpdateTestCaseCommand(
            TestCaseId.fromString(testCaseId),
            AuthorId.fromUserId(userId),
            userRole,
            toOptionalInput(request.input()),
            toOptionalExpectedOutput(request.expectedOutput()),
            toOptionalHidden(request.isHidden())
        );
    }

    public static DeleteTestCaseCommand toDeleteTestCaseCommandFromRequest(
        String testCaseId,
        UserId userId,
        UserRole userRole
    ) {
        return new DeleteTestCaseCommand(
            AuthorId.fromUserId(userId),
            userRole,
            TestCaseId.fromString(testCaseId)
        );
    }

    private static Optional<TestCaseInput> toOptionalInput(String input) {
        if (input == null) {
            return Optional.empty();
        }

        return Optional.of(TestCaseInput.fromString(input));
    }

    private static Optional<TestCaseExpectedOutput> toOptionalExpectedOutput(
        String expectedOutput
    ) {
        if (expectedOutput == null) {
            return Optional.empty();
        }

        return Optional.of(TestCaseExpectedOutput.fromString(expectedOutput));
    }

    private static Optional<TestCaseHidden> toOptionalHidden(Boolean isHidden) {
        if (isHidden == null) {
            return Optional.empty();
        }

        return Optional.of(TestCaseHidden.fromBoolean(isHidden));
    }
}