package com.codexp.challenges.challenges.domain.model.commands;

import com.codexp.challenges.challenges.domain.model.valueobjects.AuthorId;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;

public record DeleteCodeTemplateCommand(
    AuthorId authorId,
    UserRole authorRole,
    CodeTemplateId codeTemplateId
) {
}
