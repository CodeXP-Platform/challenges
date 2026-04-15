package com.codexp.challenges.challenges.interfaces.rest;

import com.codexp.challenges.challenges.domain.exceptions.TestCaseNotFoundException;
import com.codexp.challenges.challenges.domain.services.TestCaseCommandService;
import com.codexp.challenges.challenges.domain.services.TestCaseQueryService;
import com.codexp.challenges.challenges.interfaces.rest.requests.CreateTestCaseRequest;
import com.codexp.challenges.challenges.interfaces.rest.requests.UpdateTestCaseRequest;
import com.codexp.challenges.challenges.interfaces.rest.responses.TestCaseResponse;
import com.codexp.challenges.challenges.interfaces.rest.transformers.TestCaseAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.TestCaseCommandAssembler;
import com.codexp.challenges.challenges.interfaces.rest.transformers.TestCaseQueryAssembler;
import com.codexp.challenges.shared.application.UserContext;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/challenges/{challengeId}/test-cases")
public class TestCaseController {

    private final TestCaseCommandService testCaseCommandService;
    private final TestCaseQueryService testCaseQueryService;
    private final UserContext userContext;

    public TestCaseController(
        TestCaseCommandService testCaseCommandService,
        TestCaseQueryService testCaseQueryService,
        UserContext userContext
    ) {
        this.testCaseCommandService = testCaseCommandService;
        this.testCaseQueryService = testCaseQueryService;
        this.userContext = userContext;
    }

    @PostMapping
    public ResponseEntity<TestCaseResponse> create(
        @PathVariable UUID challengeId,
        @RequestBody CreateTestCaseRequest request
    ) {
        var jwt = userContext.getPrincipal();

        var command = TestCaseCommandAssembler.toAddTestCaseCommandFromRequest(
            request,
            challengeId.toString(),
            jwt.userId(),
            jwt.role()
        );

        var testCaseId = testCaseCommandService.handle(command);

        var query = TestCaseQueryAssembler.toGetTestCaseByIdQuery(testCaseId);
        var testCase = testCaseQueryService.handle(query);
        var response = TestCaseAssembler.toResponseFromEntity(testCase);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseResponse> findById(
        @PathVariable UUID challengeId,
        @PathVariable UUID id
    ) {
        var query = TestCaseQueryAssembler.toGetTestCaseByIdQuery(id.toString());
        var testCase = testCaseQueryService.handle(query);

        if (!testCase.getChallengeId().toString().equals(challengeId.toString())) {
            throw new TestCaseNotFoundException();
        }

        var response = TestCaseAssembler.toResponseFromEntity(testCase);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TestCaseResponse>> findByChallengeId(
        @PathVariable UUID challengeId
    ) {
        var query = TestCaseQueryAssembler.toGetTestCasesByChallengeIdQuery(
            challengeId.toString()
        );

        var testCases = testCaseQueryService.handle(query);

        var responses = testCases
            .stream()
            .map(TestCaseAssembler::toResponseFromEntity)
            .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TestCaseResponse> updatePartial(
        @PathVariable UUID challengeId,
        @PathVariable UUID id,
        @RequestBody UpdateTestCaseRequest request
    ) {
        assertTestCaseBelongsToChallenge(challengeId, id);

        var jwt = userContext.getPrincipal();
        var command = TestCaseCommandAssembler.toUpdateTestCaseCommandFromRequest(
            request,
            id.toString(),
            jwt.userId(),
            jwt.role()
        );

        var updatedTestCase = testCaseCommandService.handle(command);
        var response = TestCaseAssembler.toResponseFromEntity(updatedTestCase);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable UUID challengeId,
        @PathVariable UUID id
    ) {
        assertTestCaseBelongsToChallenge(challengeId, id);

        var jwt = userContext.getPrincipal();
        var command = TestCaseCommandAssembler.toDeleteTestCaseCommandFromRequest(
            id.toString(),
            jwt.userId(),
            jwt.role()
        );

        testCaseCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    private void assertTestCaseBelongsToChallenge(UUID challengeId, UUID testCaseId) {
        var query = TestCaseQueryAssembler.toGetTestCaseByIdQuery(testCaseId.toString());
        var testCase = testCaseQueryService.handle(query);

        if (!testCase.getChallengeId().toString().equals(challengeId.toString())) {
            throw new TestCaseNotFoundException();
        }
    }
}