package com.esprit.cloudcraft.services.userServices;

import com.esprit.cloudcraft.dto.userdto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.userdto.AuthenticationResponse;
import com.esprit.cloudcraft.dto.userdto.VerificationRequest;
import com.esprit.cloudcraft.entities.userEntities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
public interface AuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    public void revokeAllUserTokens(User user);
    public void saveUserToken(User user, String jwtToken);
    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest);
    public void updateToken(User user, String jwtToken);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
