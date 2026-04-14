package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.queries.GetTestCaseByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetTestCasesByChallengeIdQuery;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;

public class TestCaseQueryAssembler {

    public static GetTestCaseByIdQuery toGetTestCaseByIdQuery(String testCaseId) {
        return new GetTestCaseByIdQuery(TestCaseId.fromString(testCaseId));
    }

    public static GetTestCaseByIdQuery toGetTestCaseByIdQuery(
        TestCaseId testCaseId
    ) {
        return new GetTestCaseByIdQuery(testCaseId);
    }

    public static GetTestCasesByChallengeIdQuery toGetTestCasesByChallengeIdQuery(
        String challengeId
    ) {
        return new GetTestCasesByChallengeIdQuery(ChallengeId.fromString(challengeId));
    }

    public static GetTestCasesByChallengeIdQuery toGetTestCasesByChallengeIdQuery(
        ChallengeId challengeId
    ) {
        return new GetTestCasesByChallengeIdQuery(challengeId);
    }
}