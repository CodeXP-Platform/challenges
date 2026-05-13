package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.domain.model.queries.ExistsTestCaseByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetTestCaseByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetTestCasesByCodeTemplateIdQuery;

import java.util.List;

public interface TestCaseQueryService {
    TestCase handle(GetTestCaseByIdQuery query);

    List<TestCase> handle(GetTestCasesByCodeTemplateIdQuery query);

    boolean handle(ExistsTestCaseByIdQuery query);
}