package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.queries.ExistsCodeTemplateByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetCodeTemplateByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetCodeTemplatesByChallengeIdQuery;

import java.util.List;

public interface CodeTemplateQueryService {
    CodeTemplate handle(GetCodeTemplateByIdQuery query);

    List<CodeTemplate> handle(GetCodeTemplatesByChallengeIdQuery query);

    boolean handle(ExistsCodeTemplateByIdQuery query);
}