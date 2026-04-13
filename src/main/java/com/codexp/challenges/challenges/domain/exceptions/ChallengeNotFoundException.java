package com.codexp.challenges.challenges.domain.exceptions;

public class ChallengeNotFoundException extends RuntimeException {
    public ChallengeNotFoundException() {
        super("Challenge was not found.");
    }
}
