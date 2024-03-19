package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.User;

public interface AdminService {
    public  boolean lockUser(String user);
    public  boolean unlockUser(String user);
}
