package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.domain.model.commands.AddTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateTestCaseCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;

public interface TestCaseCommandService {
    TestCaseId handle(AddTestCaseCommand command);

    TestCase handle(UpdateTestCaseCommand command);

    void handle(DeleteTestCaseCommand command);
}