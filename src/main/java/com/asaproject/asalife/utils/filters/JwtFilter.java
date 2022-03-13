package com.asaproject.asalife.utils.filters;

import com.asaproject.asalife.domains.models.JwtConfig;
import com.asaproject.asalife.services.JwtServiceImpl;
import com.asaproject.asalife.services.UserServiceImpl;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;

    private final JwtServiceImpl jwtServiceImpl;

    private final UserServiceImpl userService;

    public JwtFilter(JwtConfig jwtConfig, JwtServiceImpl jwtServiceImpl, UserServiceImpl userService) {
        this.jwtConfig = jwtConfig;
        this.jwtServiceImpl = jwtServiceImpl;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String token = null;
            String nrp = null;

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
                token = authorizationHeader.substring(7);
                nrp = jwtServiceImpl.extractUsername(token);
            }

            if (nrp != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(nrp);
                if (jwtServiceImpl.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException | SignatureException e) {
            response.setContentType("application/json");
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is not as expected");
            } catch (IOException ex) {
                ex.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (InvalidBearerTokenException e) {
            response.setContentType("application/json");
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
            } catch (IOException ex) {
                ex.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "There is something wrong with your JWT");
            } catch (IOException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
