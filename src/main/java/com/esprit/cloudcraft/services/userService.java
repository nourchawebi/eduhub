package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.user;
import com.esprit.cloudcraft.exceptions.userAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface userService   {
    user register(user user)throws userAlreadyExistException;
     void sendRegistrationConfirmationEmail(user user);
    boolean verifyUser(String token);



}
