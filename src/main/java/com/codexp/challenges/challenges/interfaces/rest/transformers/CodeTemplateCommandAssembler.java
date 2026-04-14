package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.CreateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.EntryFunctionName;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateCode;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateCodeTemplateRequest;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

public class CodeTemplateCommandAssembler {

    public static CreateCodeTemplateCommand toCreateCodeTemplateCommandFromRequest(
        CreateCodeTemplateRequest request,
        String challengeId,
        UserId userId,
        UserRole userRole
    ) {
        return new CreateCodeTemplateCommand(
            AuthorId.fromUserId(userId),
            userRole,
            ChallengeId.fromString(challengeId),
            EntryFunctionName.fromString(request.entryFunctionName()),
            TemplateLanguage.fromString(request.language()),
            TemplateCode.fromString(request.templateCode())
        );
    }
}