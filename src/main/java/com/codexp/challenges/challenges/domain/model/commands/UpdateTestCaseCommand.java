package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseExpectedOutput;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseHidden;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseInput;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import java.util.Optional;

public record UpdateTestCaseCommand(
    TestCaseId testCaseId,
    AuthorId authorId,
    UserRole authorRole,
    Optional<TestCaseInput> input,
    Optional<TestCaseExpectedOutput> expectedOutput,
    Optional<TestCaseHidden> isHidden
) {
    public boolean hasChanges() {
        return input.isPresent() ||
        expectedOutput.isPresent() ||
        isHidden.isPresent();
    }
}
