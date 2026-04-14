package com.codexp.challenges.challenges.interfaces.rest;

import com.codexp.challenges.challenges.domain.exceptions.CodeTemplateNotFoundException;
import com.codexp.challenges.challenges.domain.services.CodeTemplateCommandService;
import com.codexp.challenges.challenges.domain.services.CodeTemplateQueryService;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateCodeTemplateRequest;
import com.codexp.challenges.challenges.interfaces.rest.responses.CodeTemplateResponse;
import com.codexp.challenges.challenges.interfaces.rest.transformers.CodeTemplateAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.CodeTemplateCommandAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.CodeTemplateQueryAssembler;
import com.codexp.challenges.shared.application.UserContext;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges/{challengeId}/code-templates")
public class CodeTemplateController {

    private final CodeTemplateCommandService codeTemplateCommandService;
    private final CodeTemplateQueryService codeTemplateQueryService;
    private final UserContext userContext;

    public CodeTemplateController(
        CodeTemplateCommandService codeTemplateCommandService,
        CodeTemplateQueryService codeTemplateQueryService,
        UserContext userContext
    ) {
        this.codeTemplateCommandService = codeTemplateCommandService;
        this.codeTemplateQueryService = codeTemplateQueryService;
        this.userContext = userContext;
    }

    @PostMapping
    public ResponseEntity<CodeTemplateResponse> create(
        @PathVariable UUID challengeId,
        @RequestBody CreateCodeTemplateRequest request
    ) {
        var jwt = userContext.getPrincipal();

        var command =
            CodeTemplateCommandAssembler.toCreateCodeTemplateCommandFromRequest(
                request,
                challengeId.toString(),
                jwt.userId(),
                jwt.role()
            );

        var codeTemplateId = codeTemplateCommandService.handle(command);

        var query = CodeTemplateQueryAssembler.toGetCodeTemplateByIdQuery(
            codeTemplateId
        );

        var codeTemplate = codeTemplateQueryService.handle(query);
        var response = CodeTemplateAssembler.toResponseFromEntity(codeTemplate);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodeTemplateResponse> findById(
        @PathVariable UUID challengeId,
        @PathVariable UUID id
    ) {
        var query = CodeTemplateQueryAssembler.toGetCodeTemplateByIdQuery(
            id.toString()
        );

        var codeTemplate = codeTemplateQueryService.handle(query);

        if (!codeTemplate.getChallengeId().toString().equals(challengeId.toString())) {
            throw new CodeTemplateNotFoundException();
        }

        var response = CodeTemplateAssembler.toResponseFromEntity(codeTemplate);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CodeTemplateResponse>> findByChallengeId(
        @PathVariable UUID challengeId
    ) {
        var query =
            CodeTemplateQueryAssembler.toGetCodeTemplatesByChallengeIdQuery(
                challengeId.toString()
            );

        var codeTemplates = codeTemplateQueryService.handle(query);

        var responses = codeTemplates
            .stream()
            .map(CodeTemplateAssembler::toResponseFromEntity)
            .toList();

        return ResponseEntity.ok(responses);
    }
}