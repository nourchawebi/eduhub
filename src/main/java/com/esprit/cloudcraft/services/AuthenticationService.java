package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.authentification.JwtService;
import com.esprit.cloudcraft.dto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.dto.registerRequest;
import com.esprit.cloudcraft.entities.RoleType;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.repository.UserRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
   private final AuthenticationManager authenticationManager;
    @Resource
    private UserRepository userRepository;
    @Resource
    private JwtService jwtService;
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    }
}
