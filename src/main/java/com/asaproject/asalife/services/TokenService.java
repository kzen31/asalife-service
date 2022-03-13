package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.NewTokenRequest;
import com.asaproject.asalife.domains.models.responses.TokenResponse;

public interface TokenService {
    Boolean verifyUser(String nrp);

    Exception authUser(String nrp, String password);

    String refreshToken(String nrp);

    TokenResponse getNewToken(NewTokenRequest request) throws Exception;

    TokenResponse getToken(User user);
}
