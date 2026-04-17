package com.codexp.challenges.challenges.domain.model.events;

import java.time.Instant;
import java.util.UUID;

public record SolutionRequestedEvent(
    UUID eventId,
    String eventType,
    Instant timestamp,
    Data data
) {

    public static SolutionRequestedEvent create(
        String challengeId,
        String authorId,
        String language,
        String templateCode
    ) {
        return new SolutionRequestedEvent(
            UUID.randomUUID(),
            "SolutionRequestedEvent",
            Instant.now(),
            new Data(challengeId, authorId, language, templateCode)
        );
    }

    public UUID eventId() {
        return eventId;
    }

    public String eventType() {
        return eventType;
    }

    public Instant timestamp() {
        return timestamp;
    }

    public Data data() {
        return data;
    }

    public record Data(
        String challengeId,
        String authorId,
        String language,
        String templateCode
    ) {
        public String challengeId() {
            return challengeId;
        }

        public String authorId() {
            return authorId;
        }

        public String language() {
            return language;
        }

        public String templateCode() {
            return templateCode;
        }
    }
}
