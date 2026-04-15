package com.codexp.challenges.challenges.domain.exceptions;

public class TestCaseNotFoundException extends RuntimeException {

    public TestCaseNotFoundException() {
        super("Test case was not found");
    }
}
