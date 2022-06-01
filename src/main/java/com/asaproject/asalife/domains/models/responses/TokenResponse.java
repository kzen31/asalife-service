package com.asaproject.asalife.domains.models.responses;

import com.asaproject.asalife.domains.ERole;
import com.asaproject.asalife.domains.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class TokenResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
    private final String refreshToken;
    private final Collection<ERole> roles;
    private final User user;
}
