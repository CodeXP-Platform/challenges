package com.codexp.challenges.challenges.interfaces.rest;

import com.codexp.challenges.challenges.domain.services.ChallengeCommandService;
import com.codexp.challenges.challenges.domain.services.ChallengeQueryService;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challenges.challenges.interfaces.rest.responses.ChallengeResponse;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeCommandAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.ChallengeQueryAssembler;
import com.codexp.challenges.shared.application.UserContext;
import java.util.UUID;
import org.springframework.data.domain.Page;
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
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<Page<ChallengeResponse>> findAll(
        @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChallengeResponse> update(
        @PathVariable UUID id,
        @RequestBody UpdateChallengeRequest request
    ) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(null);
    }
}
