package com.codexp.challenges.challenges.application.commandservices;

import com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException;
import com.codexp.challenges.challenges.domain.exceptions.CodeTemplateNotFoundException;
import com.codexp.challenges.challenges.domain.model.CodeTemplate;
import com.codexp.challenges.challenges.domain.model.commands.CreateCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.commands.DeleteCodeTemplateCommand;
import com.codexp.challenges.challenges.domain.model.commands.UpdateCodeTemplateCommand;
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

    @Override
    public CodeTemplate handle(UpdateCodeTemplateCommand command) {
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can update challenge code templates"
            );
        }

        if (!command.hasChanges()) {
            throw new IllegalArgumentException(
                "At least one code template field must be provided for update"
            );
        }

        var codeTemplate = codeTemplateRepository
            .findById(command.codeTemplateId())
            .orElseThrow(CodeTemplateNotFoundException::new);

        var challenge = challengeRepository
            .findById(codeTemplate.getChallengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can update code templates"
            );
        }

        codeTemplate.updatePartially(
            command.entryFunctionName(),
            command.language(),
            command.templateCode()
        );

        return codeTemplateRepository.save(codeTemplate);
    }

    @Override
    public void handle(DeleteCodeTemplateCommand command) {
        if (!command.authorRole().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can delete challenge code templates"
            );
        }

        var codeTemplate = codeTemplateRepository
            .findById(command.codeTemplateId())
            .orElseThrow(CodeTemplateNotFoundException::new);

        var challenge = challengeRepository
            .findById(codeTemplate.getChallengeId())
            .orElseThrow(ChallengeNotFoundException::new);

        if (!challenge.isOwnedBy(command.authorId())) {
            throw new UnauthorizedActionException(
                "Only the challenge owner can delete code templates"
            );
        }

        codeTemplateRepository.delete(codeTemplate);
    }
}
