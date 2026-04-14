package com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories;

import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeTemplateRepository
    extends JpaRepository<CodeTemplate, CodeTemplateId> {
    List<CodeTemplate> findByChallengeId(ChallengeId challengeId);
}