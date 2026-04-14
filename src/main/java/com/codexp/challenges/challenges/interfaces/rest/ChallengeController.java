package com.codexp.challenges.challenges.interfaces.rest;

import com.codexp.challenges.challenges.domain.services.ChallengeCommandService;
import com.codexp.challenges.challenges.domain.services.ChallengeQueryService;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateSolutionRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.responses.ChallengeResponse;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeCommandAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeQueryAssembler;
import com.codexp.challenges.shared.application.UserContext;
import com.codexp.challenges.shared.domain.exceptions.UnauthorizedActionException;
import com.codexp.challenges.shared.domain.model.valueobjects.UserRole;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final ChallengeCommandService challengeCommandService;
    private final ChallengeQueryService challengeQueryService;
    private final UserContext userContext;

    public ChallengeController(
        ChallengeCommandService challengeCommandService,
        ChallengeQueryService challengeQueryService,
        UserContext userContext
    ) {
        this.challengeCommandService = challengeCommandService;
        this.challengeQueryService = challengeQueryService;
        this.userContext = userContext;
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
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        var challenges = title == null || title.isBlank()
            ? challengeQueryService.handle(
                ChallengeQueryAssembler.toGetAllChallengesQuery()
            )
            : challengeQueryService.handle(
                ChallengeQueryAssembler.toGetChallengesByTitleQuery(title)
            );

        var responses = challenges
            .stream()
            .map(ChallengeAssembler::toResponseFromEntity)
            .toList();

        var pagedResponses = toPage(responses, pageable);

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
    public ResponseEntity<Void> createSolution(
        @PathVariable UUID id,
        @RequestBody CreateSolutionRequest request
    ) {
        var jwt = userContext.getPrincipal();

        if (!jwt.role().equals(UserRole.ROLE_STUDENT)) {
            throw new UnauthorizedActionException(
                "Only students can submit solutions"
            );
        }
        // TODO: Implement the logic to emmit an evento to create a solution for the current user.

        if (id == null || request == null) {
            throw new IllegalArgumentException("Invalid solution payload");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    private static Page<ChallengeResponse> toPage(
        List<ChallengeResponse> responses,
        Pageable pageable
    ) {
        int start = Math.toIntExact(pageable.getOffset());

        if (start >= responses.size()) {
            return new PageImpl<>(List.of(), pageable, responses.size());
        }

        int end = Math.min(start + pageable.getPageSize(), responses.size());
        return new PageImpl<>(responses.subList(start, end), pageable, responses.size());
    }
}
