package com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories;

import com.codexp.challenges.challenges.domain.model.TestCase;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TestCaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository
    extends JpaRepository<TestCase, TestCaseId> {
    List<TestCase> findByCodeTemplateId(CodeTemplateId codeTemplateId);

    void deleteByCodeTemplateId(CodeTemplateId codeTemplateId);
}