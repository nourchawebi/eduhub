package com.esprit.cloudcraft.services.userServices;

public interface AdminService {
    public  boolean lockUser(String user);
    public  boolean unlockUser(String user);
}
