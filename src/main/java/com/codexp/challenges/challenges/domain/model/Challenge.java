package com.codexp.challenges.challenges.domain.model;

import com.codexp.challenges.challenges.domain.model.valueobjects.*;
import com.codexp.challenges.shared.domain.model.AbstractEntity;
import jakarta.persistence.*;
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

    public boolean isOwnedBy(AuthorId author) {
        return authorId.equals(author);
    }

    public boolean isPublished() {
        return isPublished;
    }

}
