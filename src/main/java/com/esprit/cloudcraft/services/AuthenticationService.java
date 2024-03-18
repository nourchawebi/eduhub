package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.authentification.JwtService;
import com.esprit.cloudcraft.dto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.entities.token.Token;
import com.esprit.cloudcraft.entities.token.TokenType;
import com.esprit.cloudcraft.repository.SecureTokenRepository;
import com.esprit.cloudcraft.repository.TokenRepository;
import com.esprit.cloudcraft.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    @Resource
    private UserRepository userRepository;

    @Resource
    private JwtService jwtService;
    @Resource
    private TokenRepository authTokenRepository;
    @Resource
    SecureTokenRepository emailTokenRepository;


    public AuthenticationResponse authenticate(AuthenticationRequest request)  {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            // Handle authentication failure, e.g., incorrect credentials
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public void revokeAllUserTokens(User user) {
        var validUserTokens =  authTokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t  -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        authTokenRepository.saveAll(validUserTokens);
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        authTokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsernameFromToken(refreshToken);//because mainly with spring boot we talk about usernames
        if (userEmail != null ) {
            var  user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.validateToken(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .mfaEnabled(false)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }


}
