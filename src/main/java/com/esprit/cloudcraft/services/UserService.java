package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.entities.User;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
   AuthenticationResponse register(User user);
     void sendRegistrationConfirmationEmail(User user);
    boolean verifyUser(String token);




}
