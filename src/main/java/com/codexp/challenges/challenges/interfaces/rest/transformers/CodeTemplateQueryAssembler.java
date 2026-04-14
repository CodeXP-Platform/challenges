package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.queries.GetCodeTemplateByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetCodeTemplatesByChallengeIdQuery;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;

public class CodeTemplateQueryAssembler {

    public static GetCodeTemplateByIdQuery toGetCodeTemplateByIdQuery(
        String codeTemplateId
    ) {
        return new GetCodeTemplateByIdQuery(
            CodeTemplateId.fromString(codeTemplateId)
        );
    }

    public static GetCodeTemplateByIdQuery toGetCodeTemplateByIdQuery(
        CodeTemplateId codeTemplateId
    ) {
        return new GetCodeTemplateByIdQuery(codeTemplateId);
    }

    public static GetCodeTemplatesByChallengeIdQuery toGetCodeTemplatesByChallengeIdQuery(
        String challengeId
    ) {
        return new GetCodeTemplatesByChallengeIdQuery(
            ChallengeId.fromString(challengeId)
        );
    }

    public static GetCodeTemplatesByChallengeIdQuery toGetCodeTemplatesByChallengeIdQuery(
        ChallengeId challengeId
    ) {
        return new GetCodeTemplatesByChallengeIdQuery(challengeId);
    }
}