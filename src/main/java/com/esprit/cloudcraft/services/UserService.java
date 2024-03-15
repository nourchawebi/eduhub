package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.exceptions.UserAlreadyExistException;

public interface UserService {
    User register(User user)throws UserAlreadyExistException;
     void sendRegistrationConfirmationEmail(User user);
    boolean verifyUser(String token);



}
