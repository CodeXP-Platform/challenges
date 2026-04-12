package com.codexp.challengessolutions.challenges.interfaces.rest;

import java.util.UUID;

import com.codexp.challengessolutions.challenges.domain.services.ChallengeCommandService;
import com.codexp.challengessolutions.challenges.interfaces.rest.requests.CreateChallengeRequest;
import com.codexp.challengessolutions.challenges.interfaces.rest.requests.UpdateChallengeRequest;
import com.codexp.challengessolutions.challenges.interfaces.rest.responses.ChallengeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final ChallengeCommandService challengeCommandService;

    public ChallengeController(ChallengeCommandService challengeCommandService) {
        this.challengeCommandService = challengeCommandService;
    }

    @PostMapping
    public ResponseEntity<ChallengeResponse> create(@RequestBody CreateChallengeRequest request) {

        var command =
        return ResponseEntity.ok(null);
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
