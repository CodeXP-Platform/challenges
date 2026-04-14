package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.interfaces.rest.responses.TestCaseResponse;

public class TestCaseAssembler {

    public static TestCaseResponse toResponseFromEntity(TestCase entity) {
        return new TestCaseResponse(
            entity.getId().toString(),
            entity.getChallengeId().toString(),
            entity.getInput().toString(),
            entity.getExpectedOutput().toString(),
            entity.getIsHidden().value(),
            entity.getUpdatedAt(),
            entity.getCreatedAt()
        );
    }
}