package com.codexp.challengessolutions.shared.domain.model.valueobjects;

import java.util.Locale;

public enum UserRole {
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_ADMIN;

    public static UserRole fromClaim(String rawRole) {
        if (rawRole == null || rawRole.isBlank()) {
            throw new IllegalArgumentException("Role claim is missing");
        }

        return UserRole.valueOf(rawRole.trim().toUpperCase(Locale.ROOT));
    }

    public String asAuthority() {
        return "ROLE_" + name();
    }
}
