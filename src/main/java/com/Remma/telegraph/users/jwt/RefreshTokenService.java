package com.Remma.telegraph.users.jwt;

import com.Remma.telegraph.users.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository tokenRepository;

    public RefreshTokenService(RefreshTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public RefreshToken createRefreshToken(UserEntity user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusSeconds(60 * 60 * 24 * 7));

        return tokenRepository.save(refreshToken);
    }

    public RefreshToken verifyAndGet(String token) {
        RefreshToken refreshToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Refresh token not found"));

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            tokenRepository.delete(refreshToken);
            throw new AccountExpiredException("Refresh token expired, please login again");
        }

        return refreshToken;
    }

    @Transactional
    public void deleteByUser(UserEntity user) {
        tokenRepository.deleteByUser(user);
    }

}
