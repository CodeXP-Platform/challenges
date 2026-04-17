package com.codexp.challenges.challenges.domain.model;

import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import com.codexp.challenges.shared.domain.model.AbstractEntity;
import jakarta.persistence.*;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends AbstractEntity {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "challenge_id"))
    private ChallengeId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "title"))
    private ChallengeTitle title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private ChallengeDescription description;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "authorId"))
    private AuthorId authorId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "difficulty"))
    private ChallengeDifficulty difficulty;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "rewardPoints"))
    private RewardPoints rewardPoints;

    @Column(name = "isPublished")
    private Boolean isPublished;

    public static Challenge create(
            ChallengeId challengeId,
            ChallengeTitle challengeTitle,
            ChallengeDescription challengeDescription,
            AuthorId authorId,
            ChallengeDifficulty challengeDifficulty,
            RewardPoints rewardPoints
    ) {
        Challenge challenge = new Challenge();

        challenge.id = challengeId;
        challenge.title = challengeTitle;
        challenge.description = challengeDescription;
        challenge.authorId = authorId;
        challenge.difficulty = challengeDifficulty;
        challenge.rewardPoints = rewardPoints;
        challenge.isPublished = false;

        return challenge;
    }

    public ChallengeId getId() {
        return id;
    }

    public ChallengeTitle getTitle() {
        return title;
    }

    public ChallengeDescription getDescription() {
        return description;
    }

    public AuthorId getAuthorId() {
        return authorId;
    }

    public ChallengeDifficulty getDifficulty() {
        return difficulty;
    }

    public RewardPoints getRewardPoints() {
        return rewardPoints;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public boolean isOwnedBy(AuthorId author) {
        return authorId.equals(author);
    }

    public boolean isPublished() {
        return Boolean.TRUE.equals(isPublished);
    }

    public void publish() {
        this.isPublished = true;
    }

    public void updatePartially(
            Optional<ChallengeTitle> title,
            Optional<ChallengeDescription> description,
            Optional<ChallengeDifficulty> difficulty,
            Optional<RewardPoints> rewardPoints
    ) {
        title.ifPresent(value -> this.title = value);
        description.ifPresent(value -> this.description = value);
        difficulty.ifPresent(value -> this.difficulty = value);
        rewardPoints.ifPresent(value -> this.rewardPoints = value);
    }

}
