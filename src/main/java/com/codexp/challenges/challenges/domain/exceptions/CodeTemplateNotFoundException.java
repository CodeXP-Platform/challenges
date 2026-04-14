package com.codexp.challenges.challenges.domain.exceptions;

public class CodeTemplateNotFoundException extends RuntimeException {

    public CodeTemplateNotFoundException() {
        super("Code template was not found");
    }
}
