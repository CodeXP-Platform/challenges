package com.codexp.challenges.challenges.interfaces.rest.transformers;

import com.codexp.challenges.challenges.domain.model.commands.CreateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.EntryFunctionName;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateCode;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateCodeTemplateRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateCodeTemplateRequest;
import com.codexp.challenges.shared.domain.model.valueobjects.UserId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import java.util.Optional;

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

    public static UpdateCodeTemplateCommand toUpdateCodeTemplateCommandFromRequest(
        UpdateCodeTemplateRequest request,
        String codeTemplateId,
        UserId userId,
        UserRole userRole
    ) {
        return new UpdateCodeTemplateCommand(
            CodeTemplateId.fromString(codeTemplateId),
            AuthorId.fromUserId(userId),
            userRole,
            toOptionalEntryFunctionName(request.entryFunctionName()),
            toOptionalTemplateLanguage(request.language()),
            toOptionalTemplateCode(request.templateCode())
        );
    }

    public static DeleteCodeTemplateCommand toDeleteCodeTemplateCommandFromRequest(
        String codeTemplateId,
        UserId userId,
        UserRole userRole
    ) {
        return new DeleteCodeTemplateCommand(
            AuthorId.fromUserId(userId),
            userRole,
            CodeTemplateId.fromString(codeTemplateId)
        );
    }

    private static Optional<EntryFunctionName> toOptionalEntryFunctionName(
        String entryFunctionName
    ) {
        if (entryFunctionName == null) {
            return Optional.empty();
        }

        return Optional.of(EntryFunctionName.fromString(entryFunctionName));
    }

    private static Optional<TemplateLanguage> toOptionalTemplateLanguage(
        String language
    ) {
        if (language == null) {
            return Optional.empty();
        }

        return Optional.of(TemplateLanguage.fromString(language));
    }

    private static Optional<TemplateCode> toOptionalTemplateCode(
        String templateCode
    ) {
        if (templateCode == null) {
            return Optional.empty();
        }

        return Optional.of(TemplateCode.fromString(templateCode));
    }
}