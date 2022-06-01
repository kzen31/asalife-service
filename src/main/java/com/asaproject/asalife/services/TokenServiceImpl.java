package com.asaproject.asalife.services;

import com.asaproject.asalife.domains.entities.RefreshToken;
import com.asaproject.asalife.domains.entities.User;
import com.asaproject.asalife.domains.models.requests.NewTokenRequest;
import com.asaproject.asalife.domains.models.responses.TokenResponse;
import com.asaproject.asalife.repositories.UserRepository;
import com.asaproject.asalife.utils.mappers.RoleUserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Boolean checkObject(Object object) {
        return ObjectUtils.isEmpty(object);
    }

    @Override
    public Boolean verifyUser(String nrp) {
        return !ObjectUtils.isEmpty(userRepo.findByNrp(nrp));
    }

    @Override
    public Exception authUser(String nrp, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nrp, password));
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    public String refreshToken(String nrp) {
        Boolean userState = verifyUser(nrp);
        if (userState) {
            User user = userRepo.findByNrp(nrp);
            assert user != null;
            return refreshTokenService.createRefreshToken(user.getId()).getToken();
        } else
            return null;
    }

    @Override
    public TokenResponse getNewToken(NewTokenRequest request) throws Exception {
        String refreshToken = request.getRefreshToken();

        if (checkObject(refreshToken)) {
            throw new Exception("Bad Request");
        }
        RefreshToken token = refreshTokenService.findByToken(refreshToken);

        if (checkObject(token)) {
            throw new Exception("Token Not Found");
        }

        Boolean notExpire = refreshTokenService.verifyExpiration(token);
        if (!notExpire) {
            throw new Exception("Token Not Found");
        }

        String newToken = jwtService.generateToken(token.getUser().getUsername());
        return new TokenResponse(newToken, refreshToken, RoleUserMapper.rolesToERoles(token.getUser().getRoles()), token.getUser());
    }

    @Override
    public TokenResponse getToken(User user) {
        String jwtToken = jwtService.generateToken(user.getNrp());
        String refreshToken = refreshToken(user.getNrp());

        return new TokenResponse(jwtToken, refreshToken, RoleUserMapper.rolesToERoles(user.getRoles()), user);
    }
}
