package com.codexp.challengessolutions.challenges.infrastructure.persistence.jpa.repositories;

import com.codexp.challengessolutions.challenges.domain.model.Challenge;
import com.codexp.challengessolutions.challenges.domain.model.valueobjects.ChallengeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository
    extends JpaRepository<Challenge, ChallengeId> {}
