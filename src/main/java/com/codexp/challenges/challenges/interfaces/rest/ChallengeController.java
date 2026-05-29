package com.codexp.challenges.challenges.interfaces.rest;

import com.codexp.challenges.challenges.domain.services.ChallengeCommandService;
import com.codexp.challenges.challenges.domain.services.ChallengeQueryService;
import com.codexp.challenges.challenges.domain.model.queries.FindChallengesQuery;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateSolutionRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.responses.ChallengeResponse;
import com.codexp.challenges.challenges.interfaces.rest.responses.RequestSolutionAcceptedResponse;
import com.codexp.challenges.challenges.interfaces.rest.responses.SubmitChallengeContextResponse;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeCommandAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeQueryAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.CodeTemplateQueryAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.TestCaseQueryAssembler;
import com.codexp.challenges.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.shared.application.UserContext;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import com.codexp.challenges.challenges.domain.services.CodeTemplateQueryService;
import com.codexp.challenges.challenges.domain.services.TestCaseQueryService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final ChallengeCommandService challengeCommandService;
    private final ChallengeQueryService challengeQueryService;
    private final ChallengeRepository challengeRepository;
    private final CodeTemplateQueryService codeTemplateQueryService;
    private final TestCaseQueryService testCaseQueryService;
    private final UserContext userContext;
    private final String internalToken;

    public ChallengeController(
        ChallengeCommandService challengeCommandService,
        ChallengeQueryService challengeQueryService,
        ChallengeRepository challengeRepository,
        CodeTemplateQueryService codeTemplateQueryService,
        TestCaseQueryService testCaseQueryService,
        UserContext userContext,
        @Value("${app.challenges.internal-token}") String internalToken
    ) {
        this.challengeCommandService = challengeCommandService;
        this.challengeQueryService = challengeQueryService;
        this.challengeRepository = challengeRepository;
        this.codeTemplateQueryService = codeTemplateQueryService;
        this.testCaseQueryService = testCaseQueryService;
        this.userContext = userContext;
        this.internalToken = internalToken;
    }

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(
        @RequestBody CreateChallengeRequest request
    ) {
        var jwt = userContext.getPrincipal();

        var command =
            ChallengeCommandAssembler.toCreateChallengeCommandFromRequest(
                request,
                jwt.userId(),
                jwt.role()
            );

        var challengeId = challengeCommandService.handle(command);

        var query = ChallengeQueryAssembler.toGetChallengeByIdQuery(
            challengeId,
            jwt.userId()
        );

        var challenge = challengeQueryService.handle(query);

        var response = ChallengeAssembler.toResponseFromEntity(challenge);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> findById(@PathVariable UUID id) {
        var jwt = userContext.getPrincipal();

        var query = ChallengeQueryAssembler.toGetChallengeByIdQuery(
            id.toString(),
            jwt.userId()
        );

        var challenge = challengeQueryService.handle(query);
        var response = ChallengeAssembler.toResponseFromEntity(challenge);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ChallengeResponse>> findAll(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String difficulty,
        @RequestParam(required = false) String language,
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        var query = FindChallengesQuery.of(title, difficulty, language);
        var pagedResponses = challengeQueryService.handle(query, pageable)
            .map(ChallengeAssembler::toResponseFromEntity);
        return ResponseEntity.ok(pagedResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeResponse> update(
        @PathVariable UUID id,
        @RequestBody UpdateChallengeRequest request
    ) {
        var jwt = userContext.getPrincipal();

        var command =
            ChallengeCommandAssembler.toUpdateChallengeCommandFromRequest(
                request,
                jwt.userId().value(),
                jwt.role(),
                id.toString()
            );

        var challenge = challengeCommandService.handle(command);
        var response = ChallengeAssembler.toResponseFromEntity(challenge);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<ChallengeResponse> publish(@PathVariable UUID id) {
        var jwt = userContext.getPrincipal();

        var command =
            ChallengeCommandAssembler.toPublishChallengeCommandFromRequest(
                id.toString(),
                jwt.userId().value(),
                jwt.role()
            );

        var challenge = challengeCommandService.handle(command);
        var response = ChallengeAssembler.toResponseFromEntity(challenge);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        var jwt = userContext.getPrincipal();

        if (!jwt.role().equals(UserRole.ROLE_TEACHER)) {
            throw new UnauthorizedActionException(
                "Only teachers can delete challenges"
            );
        }

        var command =
            ChallengeCommandAssembler.toDeleteChallengeCommandFromRequest(
                id.toString(),
                jwt.userId().value()
            );

        challengeCommandService.handle(command);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/solutions")
    public ResponseEntity<RequestSolutionAcceptedResponse> createSolution(
        @PathVariable UUID id,
        @RequestBody CreateSolutionRequest request
    ) {
        var jwt = userContext.getPrincipal();

        if (
            id == null ||
            request == null ||
            request.language() == null ||
            request.language().isBlank()
        ) {
            throw new IllegalArgumentException("Invalid solution payload");
        }

        var command = ChallengeCommandAssembler.toRequestSolutionCreationCommand(
            id.toString(),
            jwt.userId().value(),
            jwt.role(),
            request
        );
        challengeCommandService.handle(command);

        var response = new RequestSolutionAcceptedResponse(
            id.toString(),
            request.language().trim().toLowerCase(),
            "REQUESTED"
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{id}/solutions/submit-context")
    public ResponseEntity<SubmitChallengeContextResponse> getSubmitContext(
        @PathVariable UUID id,
        @RequestParam String language,
        @RequestHeader(value = "X-Internal-Token", required = false) String providedInternalToken
    ) {
        if (providedInternalToken == null || !providedInternalToken.equals(internalToken)) {
            throw new UnauthorizedActionException("Invalid internal token");
        }

        var challenge = challengeRepository
            .findById(ChallengeId.fromString(id.toString()))
            .orElseThrow(com.codexp.challenges.challenges.domain.exceptions.ChallengeNotFoundException::new);
        if (!challenge.isPublished()) {
            throw new IllegalArgumentException("Challenge must be published");
        }

        var languageKey = language == null ? null : language.trim();
        if (languageKey == null || languageKey.isEmpty()) {
            throw new IllegalArgumentException("Language is required");
        }

        var templates = codeTemplateQueryService.handle(
            CodeTemplateQueryAssembler.toGetCodeTemplatesByChallengeIdQuery(id.toString())
        );
        var selectedTemplate = templates
            .stream()
            .filter(template -> template.getLanguage().value().equalsIgnoreCase(languageKey))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No template found for requested language"));

        var testCases = testCaseQueryService.handle(
            TestCaseQueryAssembler.toGetTestCasesByCodeTemplateIdQuery(selectedTemplate.getId().value().toString())
        );
        if (testCases.isEmpty()) {
            throw new IllegalArgumentException("Challenge has no test cases configured");
        }

        var response = new SubmitChallengeContextResponse(
            selectedTemplate.getTemplateCode().toString(),
            selectedTemplate.getLanguage().value(),
            selectedTemplate.getEntryFunctionName().value(),
            testCases
                .stream()
                .map(testCase ->
                    new SubmitChallengeContextResponse.SubmitTestCaseResponse(
                        testCase.getId().toString(),
                        testCase.getInput().toString(),
                        testCase.getExpectedOutput().toString(),
                        testCase.getIsHidden().value()
                    )
                )
                .toList()
        );

        return ResponseEntity.ok(response);
    }
}
