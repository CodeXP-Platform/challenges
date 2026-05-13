package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.queries.GetTestCaseByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetTestCasesByCodeTemplateIdQuery;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
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

    public static GetTestCasesByCodeTemplateIdQuery toGetTestCasesByCodeTemplateIdQuery(
        String codeTemplateId
    ) {
        return new GetTestCasesByCodeTemplateIdQuery(CodeTemplateId.fromString(codeTemplateId));
    }

    public static GetTestCasesByCodeTemplateIdQuery toGetTestCasesByCodeTemplateIdQuery(
        CodeTemplateId codeTemplateId
    ) {
        return new GetTestCasesByCodeTemplateIdQuery(codeTemplateId);
    }
}