package com.esprit.cloudcraft.services.userServices;

import com.esprit.cloudcraft.entities.userEntities.User;

import java.util.Date;
import java.util.List;

public interface AdminService {
    public  boolean lockUser(String user, String unlockdate);
    public  boolean unlockUser(String user);
    public List<User> getAllUsers();
}
