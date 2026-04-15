package com.codexp.challenges.challenges.application.queryservices;

import com.codexp.challenges.challenges.domain.exceptions.CodeTemplateNotFoundException;
import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.queries.ExistsCodeTemplateByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetCodeTemplateByIdQuery;
import com.codexp.challenges.challenges.domain.model.queries.GetCodeTemplatesByChallengeIdQuery;
import com.codexp.challenges.challenges.domain.services.CodeTemplateQueryService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.CodeTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeTemplateQueryServiceImpl implements CodeTemplateQueryService {

    private final CodeTemplateRepository codeTemplateRepository;

    public CodeTemplateQueryServiceImpl(CodeTemplateRepository codeTemplateRepository) {
        this.codeTemplateRepository = codeTemplateRepository;
    }

    @Override
    public CodeTemplate handle(GetCodeTemplateByIdQuery query) {
        return codeTemplateRepository
            .findById(query.codeTemplateId())
            .orElseThrow(CodeTemplateNotFoundException::new);
    }

    @Override
    public List<CodeTemplate> handle(GetCodeTemplatesByChallengeIdQuery query) {
        return codeTemplateRepository.findByChallengeId(query.challengeId());
    }

    @Override
    public boolean handle(ExistsCodeTemplateByIdQuery query) {
        return codeTemplateRepository.existsById(query.codeTemplateId());
    }
}
