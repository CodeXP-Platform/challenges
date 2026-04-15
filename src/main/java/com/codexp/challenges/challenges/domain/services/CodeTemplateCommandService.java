package com.codexp.challenges.challenges.domain.services;

import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.commands.CreateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;

public interface CodeTemplateCommandService {
    CodeTemplateId handle(CreateCodeTemplateCommand command);

    CodeTemplate handle(UpdateCodeTemplateCommand command);

    void handle(DeleteCodeTemplateCommand command);
}