package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

public record RequestSolutionCreationCommand(
    ChallengeId challengeId,
    AuthorId authorId,
    UserRole authorRole,
    TemplateLanguage language
) {
    public ChallengeId challengeId() {
        return challengeId;
    }

    public AuthorId authorId() {
        return authorId;
    }

    public UserRole authorRole() {
        return authorRole;
    }

    public TemplateLanguage language() {
        return language;
    }
}
