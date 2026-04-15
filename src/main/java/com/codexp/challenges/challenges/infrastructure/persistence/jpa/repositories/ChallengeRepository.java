package com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories;

import com.codexp.challenges.challenges.domain.model.Challenge;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository
    extends JpaRepository<Challenge, ChallengeId> {
    Page<Challenge> findByTitle_ValueContainingIgnoreCase(
        String title,
        Pageable pageable
    );
}
