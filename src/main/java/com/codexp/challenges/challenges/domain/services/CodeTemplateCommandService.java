package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.commands.CreateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;

public interface CodeTemplateCommandService {
    CodeTemplateId handle(CreateCodeTemplateCommand command);
}