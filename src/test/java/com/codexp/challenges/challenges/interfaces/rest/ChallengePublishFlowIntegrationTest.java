package com.codexp.challenges.challenges.interfaces.rest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.ChallengeRepository;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.CodeTemplateRepository;
import com.codexp.challenges.challenges.infrastructure.persistence.jpa.repositories.TestCaseRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ChallengePublishFlowIntegrationTest {

    private static final String TEACHER_ID = "11111111-1111-1111-1111-111111111111";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private CodeTemplateRepository codeTemplateRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @AfterEach
    void cleanUp() {
        testCaseRepository.deleteAll();
        codeTemplateRepository.deleteAll();
        challengeRepository.deleteAll();
    }

    @Test
    void publishChallengeFlow_marksAsPublished_whenPreconditionsAreMet() throws Exception {
        var challengeId = createChallenge();

        createCodeTemplate(challengeId);
        createTestCase(challengeId);

        mockMvc
            .perform(
                patch("/api/v1/challenges/{id}/publish", challengeId)
                    .header(AUTHORIZATION, bearerToken())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.challengeId").value(challengeId))
            .andExpect(jsonPath("$.isPublished").value(true));

        mockMvc
            .perform(
                get("/api/v1/challenges/{id}", challengeId)
                    .header(AUTHORIZATION, bearerToken())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isPublished").value(true));
    }

    @Test
    void publishChallengeFlow_returns422AndKeepsDraft_whenAnyPreconditionIsMissing() throws Exception {
        var challengeId = createChallenge();

        mockMvc
            .perform(
                patch("/api/v1/challenges/{id}/publish", challengeId)
                    .header(AUTHORIZATION, bearerToken())
            )
            .andExpect(status().isUnprocessableContent())
            .andExpect(jsonPath("$.code").value("Validation error"));

        mockMvc
            .perform(
                get("/api/v1/challenges/{id}", challengeId)
                    .header(AUTHORIZATION, bearerToken())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isPublished").value(false));
    }

    private String createChallenge() throws Exception {
        var requestBody = """
            {
              "title": "Two Sum Challenge",
              "description": "Return indices of numbers that add up to a target",
              "difficulty": 3,
              "rewardPoints": 25
            }
            """;

        var response = mockMvc
            .perform(
                post("/api/v1/challenges")
                    .header(AUTHORIZATION, bearerToken())
                    .contentType(APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.isPublished").value(false))
            .andReturn()
            .getResponse()
            .getContentAsString();

        JsonNode body = objectMapper.readTree(response);
        return body.get("challengeId").asText();
    }

    private void createCodeTemplate(String challengeId) throws Exception {
        var requestBody = """
            {
              "entryFunctionName": "solve",
              "language": "java",
              "templateCode": "public class Solution { public static int solve(int a, int b) { return 0; } }"
            }
            """;

        mockMvc
            .perform(
                post("/api/v1/challenges/{challengeId}/code-templates", challengeId)
                    .header(AUTHORIZATION, bearerToken())
                    .contentType(APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isCreated());
    }

    private void createTestCase(String challengeId) throws Exception {
        var requestBody = """
            {
              "input": "2 3",
              "expectedOutput": "5",
              "isHidden": false
            }
            """;

        mockMvc
            .perform(
                post("/api/v1/challenges/{challengeId}/test-cases", challengeId)
                    .header(AUTHORIZATION, bearerToken())
                    .contentType(APPLICATION_JSON)
                    .content(requestBody)
            )
            .andExpect(status().isCreated());
    }

    private String bearerToken() {
        return "Bearer " + generateTeacherToken();
    }

    private String generateTeacherToken() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        return Jwts.builder()
            .subject(TEACHER_ID)
            .claim("nickname", "docente")
            .claim("email", "docente@codexp.dev")
            .claim("role", "ROLE_TEACHER")
            .issuedAt(new java.util.Date())
            .expiration(new java.util.Date(System.currentTimeMillis() + 3600_000))
            .id(UUID.randomUUID().toString())
            .signWith(key)
            .compact();
    }
}
