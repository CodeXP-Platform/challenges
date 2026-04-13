package com.codexp.challenges.shared.domain.model.valueobjects;

public record JwtPrincipal(
    UserId userId,
    NickName nickname,
    UserEmail email,
    UserRole role
) {}