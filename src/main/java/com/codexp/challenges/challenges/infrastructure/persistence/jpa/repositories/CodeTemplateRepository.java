package com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories;

import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeTemplateRepository
    extends JpaRepository<CodeTemplate, CodeTemplateId> {
    List<CodeTemplate> findByChallengeId(ChallengeId challengeId);

    Optional<CodeTemplate> findFirstByChallengeIdAndLanguage(
        ChallengeId challengeId,
        TemplateLanguage language
    );

    void deleteByChallengeId(ChallengeId challengeId);
}
