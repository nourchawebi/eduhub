package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.*;
import com.esprit.cloudcraft.entities.User;

import java.security.Principal;

public interface UserService {
   AuthenticationResponse register(User user);
     void sendRegistrationConfirmationEmail(User user);
    boolean verifyUser(String token);
    public void resendToken(String email) throws IllegalArgumentException;
    public void sendVerifNewEmail(User user);
    public AuthenticationResponse verifyNewEmail(String token);
    public String changeEmail(ChangeEmailRequest request, Principal connectedUser);
    public void changePassword(ChangePasswordRequest request, Principal connectedUser);
    public  boolean sendForgotPasswordRequest(String email);
    public boolean setForgotPassword(ForgotPasswordRequest request);
    public boolean findByEmail(String email);
 public User getByEmail(String email);
 public AuthenticationResponse changePersonalInfos(ChangePersonalInfosdRequest request, Principal connectedUser);


}
