package com.codexp.challengessolutions.shared.infrastructure.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenReader {

    private final SecretKey signingKey;

    public JwtTokenReader(JwtProperties jwtProperties) {
        this.signingKey = resolveSigningKey(jwtProperties.getSecret());
    }

    public Claims readClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey resolveSigningKey(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("Property app.jwt.secret is required");
        }

        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        } catch (IllegalArgumentException ignored) {
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
    }
}