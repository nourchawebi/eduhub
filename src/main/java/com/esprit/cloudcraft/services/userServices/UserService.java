package com.esprit.cloudcraft.services.userServices;

import com.esprit.cloudcraft.dto.userdto.*;
import com.esprit.cloudcraft.entities.userEntities.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
   public List<User> getAllUsers();
   public AuthenticationResponse register(User user);
   public void sendRegistrationConfirmationEmail(User user);
    public boolean verifyUser(String token);
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
