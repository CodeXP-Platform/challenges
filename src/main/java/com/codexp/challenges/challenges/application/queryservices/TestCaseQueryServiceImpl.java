package com.codexp.challenges.challenges.application.queryservices;

import com.codexp.challenges.challenges.domain.exceptions.TestCaseNotFoundException;
import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.domain.model.queries.ExistsTestCaseByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetTestCaseByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetTestCasesByChallengeIdQuery;
import com.codexp.challenges.challenges.domain.services.TestCaseQueryService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseQueryServiceImpl implements TestCaseQueryService {

    private final TestCaseRepository testCaseRepository;

    public TestCaseQueryServiceImpl(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @Override
    public TestCase handle(GetTestCaseByIdQuery query) {
        return testCaseRepository
            .findById(query.testCaseId())
            .orElseThrow(TestCaseNotFoundException::new);
    }

    @Override
    public List<TestCase> handle(GetTestCasesByChallengeIdQuery query) {
        return testCaseRepository.findByChallengeId(query.challengeId());
    }

    @Override
    public boolean handle(ExistsTestCaseByIdQuery query) {
        return testCaseRepository.existsById(query.testCaseId());
    }
}
