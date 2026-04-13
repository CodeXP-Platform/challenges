package com.codexp.challengessolutions.shared.domain.model.valueobjects;

public record JwtPrincipal(
    String userId,
    String nickname,
    String email,
    UserRole role
) {}