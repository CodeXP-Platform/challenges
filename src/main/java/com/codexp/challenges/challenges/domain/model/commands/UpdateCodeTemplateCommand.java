package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.model.valueobjects.EntryFunctionName;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateCode;
import com.codexp.challenges.challenges.domain.model.valueobjects.TemplateLanguage;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import java.util.Optional;

public record UpdateCodeTemplateCommand(
    CodeTemplateId codeTemplateId,
    AuthorId authorId,
    UserRole authorRole,
    Optional<EntryFunctionName> entryFunctionName,
    Optional<TemplateLanguage> language,
    Optional<TemplateCode> templateCode
) {
    public boolean hasChanges() {
        return entryFunctionName.isPresent() ||
        language.isPresent() ||
        templateCode.isPresent();
    }
}
