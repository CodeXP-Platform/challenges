package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

public record DeleteTestCaseCommand(
    AuthorId authorId,
    UserRole authorRole,
    TestCaseId testCaseId
) {
}
