package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.RefreshToken;
import com.asaproject.asalife.domains.models.requests.SignOutRequest;

public interface RefreshTokenService {
    RefreshToken findByToken(String refreshToken);

    RefreshToken createRefreshToken(Long userId);

    Boolean verifyExpiration(RefreshToken token);

    void deleteByToken(String token);

    void signOut(SignOutRequest signOutRequest) throws Exception;
}
