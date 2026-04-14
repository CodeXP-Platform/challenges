package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.interfaces.rest.responses.CodeTemplateResponse;

public class CodeTemplateAssembler {

    public static CodeTemplateResponse toResponseFromEntity(CodeTemplate entity) {
        return new CodeTemplateResponse(
            entity.getId().toString(),
            entity.getChallengeId().toString(),
            entity.getEntryFunctionName().value(),
            entity.getLanguage().value(),
            entity.getTemplateCode().toString(),
            entity.getUpdatedAt(),
            entity.getCreatedAt()
        );
    }
}