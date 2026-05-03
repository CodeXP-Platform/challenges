package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseExpectedOutput;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseHidden;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseInput;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

/**
 * Command to add a test case to a challenge.
 */
public record AddTestCaseCommand(
    AuthorId authorId,
    UserRole authorRole,
    CodeTemplateId codeTemplateId,
    TestCaseInput input,
    TestCaseExpectedOutput expectedOutput,
    TestCaseHidden isHidden
) {
}
