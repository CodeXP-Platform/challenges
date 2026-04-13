package com.codexp.challengessolutions.challenges.interfaces.rest;

import java.util.UUID;

import com.codexp.challengessolutions.challenges.domain.model.valueobjects.ChallengeId;
import com.codexp.challengessolutions.challenges.domain.services.ChallengeCommandService;
import com.codexp.challengessolutions.challenges.domain.services.ChallengeQueryService;
import com.codexp.challengessolutions.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challengessolutions.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challengessolutions.challenges.interfaces.rest.responses.ChallengeResponse;
import com.codexp.challengessolutions.challenges.interfaces.rest.transformers.ChallengeAssembler;
import com.codexp.challengessolutions.challenges.interfaces.rest.transformers.ChallengeCommandAssembler;
import com.codexp.challengessolutions.challenges.interfaces.rest.transformers.ChallengeQueryAssembler;
import com.codexp.challengessolutions.shared.infrastructure.security.JwtUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final ChallengeCommandService challengeCommandService;
    private final ChallengeQueryService challengeQueryService;
    private final JwtUtils jwtUtils;

    public ChallengeController(ChallengeCommandService challengeCommandService, ChallengeQueryService challengeQueryService, JwtUtils jwtUtils) {
        this.challengeCommandService = challengeCommandService;
        this.challengeQueryService = challengeQueryService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody CreateChallengeRequest request) {

        var userId = jwtUtils.extractUserId()

        var command = ChallengeCommandAssembler.toCreateChallengeCommandFromRequest(request, null);

        var challengeId = challengeCommandService.handle(command);

        var query = ChallengeQueryAssembler.toGetChallengeByIdQuery(challengeId);

        var result = challengeQueryService.handle(query);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var challenge = result.get();
        
        var response = ChallengeAssembler.toResponseFromEntity(challenge);

        return ResponseEntity.ok(response);
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
