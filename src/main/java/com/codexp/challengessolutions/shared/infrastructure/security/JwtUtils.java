package com.codexp.challengessolutions.shared.infrastructure.security;

import org.springframework.stereotype.Component;

import com.codexp.challengessolutions.shared.domain.model.valueobjects.JwtPrincipal;
import com.codexp.challengessolutions.shared.domain.model.valueobjects.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import java.util.Optional;

@Component
public class JwtUtils {

    private final JwtTokenReader tokenReader;

    public JwtUtils(JwtTokenReader tokenReader) {
        this.tokenReader = tokenReader;
    }

    public Claims extractAllClaims(String token) {
        return tokenReader.readClaims(token);
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractNickname(String token) {
        return extractAllClaims(token).get(JwtClaimNames.NICKNAME, String.class);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get(JwtClaimNames.EMAIL, String.class);
    }

    public UserRole extractRole(String token) {
        return UserRole.fromClaim(extractAllClaims(token).get(JwtClaimNames.ROLE, String.class));
    }

    public Optional<JwtPrincipal> extractPrincipal(String token) {
        try {
            Claims claims = extractAllClaims(token);

            String userId = claims.getSubject();
            String nickname = claims.get(JwtClaimNames.NICKNAME, String.class);
            String email = claims.get(JwtClaimNames.EMAIL, String.class);
            UserRole role = UserRole.fromClaim(claims.get(JwtClaimNames.ROLE, String.class));

            return Optional.of(new JwtPrincipal(userId, nickname, email, role));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}