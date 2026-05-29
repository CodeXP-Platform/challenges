package com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository
    extends JpaRepository<Challenge, ChallengeId> {

    Page<Challenge> findAllByIsPublishedTrue(Boolean isPublished, Pageable pageable);

    @Query(
        value = """
            SELECT c.* FROM challenge c
            WHERE c.is_published = true
              AND (CAST(:title AS text) IS NULL OR LOWER(c.title) LIKE LOWER('%' || CAST(:title AS text) || '%'))
              AND (CAST(:minDifficulty AS integer) IS NULL OR c.difficulty >= CAST(:minDifficulty AS integer))
              AND (CAST(:maxDifficulty AS integer) IS NULL OR c.difficulty <= CAST(:maxDifficulty AS integer))
              AND (CAST(:language AS text) IS NULL OR EXISTS (
                  SELECT 1 FROM code_templates ct
                  WHERE ct.challenge_id = c.challenge_id
                    AND ct.language = CAST(:language AS text)
              ))
            ORDER BY c.created_at
            """,
        countQuery = """
            SELECT count(*) FROM challenge c
            WHERE c.is_published = true
              AND (CAST(:title AS text) IS NULL OR LOWER(c.title) LIKE LOWER('%' || CAST(:title AS text) || '%'))
              AND (CAST(:minDifficulty AS integer) IS NULL OR c.difficulty >= CAST(:minDifficulty AS integer))
              AND (CAST(:maxDifficulty AS integer) IS NULL OR c.difficulty <= CAST(:maxDifficulty AS integer))
              AND (CAST(:language AS text) IS NULL OR EXISTS (
                  SELECT 1 FROM code_templates ct
                  WHERE ct.challenge_id = c.challenge_id
                    AND ct.language = CAST(:language AS text)
              ))
            """,
        nativeQuery = true
    )
    Page<Challenge> findPublishedByFilters(
        @Param("title") String title,
        @Param("minDifficulty") Integer minDifficulty,
        @Param("maxDifficulty") Integer maxDifficulty,
        @Param("language") String language,
        Pageable pageable
    );
}
