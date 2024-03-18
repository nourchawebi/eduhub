package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.dto.ChangeEmailRequest;
import com.esprit.cloudcraft.dto.ChangePasswordRequest;
import com.esprit.cloudcraft.entities.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

public interface UserService {
   AuthenticationResponse register(User user)throws UsernameNotFoundException;
     void sendRegistrationConfirmationEmail(User user);
    boolean verifyUser(String token);
    public void resendToken(String email) throws IllegalArgumentException;
    public void sendVerifNewEmail(User user);
    public boolean verifyNewEmail(String token);
    public String changeEmail(ChangeEmailRequest request, Principal connectedUser);
    public void changePassword(ChangePasswordRequest request, Principal connectedUser);




}
