package com.Remma.telegraph.users.jwt;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
