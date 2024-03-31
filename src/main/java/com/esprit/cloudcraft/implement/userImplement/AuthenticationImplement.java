package com.esprit.cloudcraft.implement.userImplement;

import com.esprit.cloudcraft.filter.JwtService;
import com.esprit.cloudcraft.dto.userdto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.userdto.AuthenticationResponse;
import com.esprit.cloudcraft.dto.userdto.VerificationRequest;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.entities.userEntities.token.Token;
import com.esprit.cloudcraft.entities.userEntities.token.TokenType;
import com.esprit.cloudcraft.repository.userDao.TokenRepository;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.userServices.AuthenticationService;
import com.esprit.cloudcraft.tfa.TwoFactorAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
@Service
@RequiredArgsConstructor
public class AuthenticationImplement  implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    @Resource
    private UserRepository userRepository;
    @Resource
    private JwtService jwtService;
    @Resource
    private TokenRepository authTokenRepository;
    @Resource
    private TwoFactorAuthenticationService tfaService;

    /********************* authenticate user methode implement **********************/
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e)
        {
            throw new BadCredentialsException("bad crednetial");
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if(user.isMfaEnabled())
        {
            return AuthenticationResponse.builder()
                    // .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                    .accessToken("")
                    .refreshToken("")
                    .mfaEnabled(true)
                    .build();
        }
        var jwtToken=jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                // .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .build();
    }

    /********************* set expired and revoked all the user tokens when he login again => 1 sessions****************/
    @Override
    public void revokeAllUserTokens(User user)
    {
        var validUserTokens =  authTokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t  -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        authTokenRepository.saveAll(validUserTokens);
    }
    /********************** save the user jwt token *******************/
    public void saveUserToken(User user, String jwtToken)
    {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .createdAt(Timestamp.from(Instant.now()))
                .revoked(false)
                .expired(false)
                .build();
        authTokenRepository.save(token);
    }


    /********************** verify the double authentication code *********************/
    @Override
    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest)
    {
        User user = userRepository
                .findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found with %S", verificationRequest.getEmail()))
                );
        if (tfaService.isOtpNotValid(user.getSecret(), verificationRequest.getCode()))
        {
            throw new BadCredentialsException("Code is not correct");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
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
