package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.commands.CreateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.valueobjects.CodeTemplateId;
import com.codexp.challenges.challenges.domain.services.CodeTemplateCommandService;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.CodeTemplateRepository;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import org.springframework.stereotype.Service;

@Service
public class CodeTemplateCommandServiceImpl implements CodeTemplateCommandService {

    private final CodeTemplateRepository codeTemplateRepository;
    private final ChallengeRepository challengeRepository;

    public CodeTemplateCommandServiceImpl(
        CodeTemplateRepository codeTemplateRepository,
        ChallengeRepository challengeRepository
    ) {
        this.codeTemplateRepository = codeTemplateRepository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public CodeTemplateId handle(CreateCodeTemplateCommand command) {
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can create challenge code templates"
            );
        }

        var challenge = challengeRepository
            .findById(command.challengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can create code templates"
            );
        }

        var codeTemplateId = CodeTemplateId.generate();

        var codeTemplate = CodeTemplate.create(
            codeTemplateId,
            command.challengeId(),
            command.entryFunctionName(),
            command.language(),
            command.templateCode()
        );

        codeTemplateRepository.save(codeTemplate);

        return codeTemplateId;
    }
}
