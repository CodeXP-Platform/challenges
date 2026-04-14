package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseExpectedOutput;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseHidden;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseInput;

/**
 * Command to add a test case to a challenge.
 */
public record AddTestCaseCommand(
    ChallengeId challengeId,
    TestCaseInput input,
    TestCaseExpectedOutput expectedOutput,
    TestCaseHidden isHidden
) {
}
