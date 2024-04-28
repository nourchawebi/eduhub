package com.esprit.cloudcraft.dto;

import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.entities.userEntities.User;
import lombok.Data;

@Data
public class Participate {
    private User user;
    private Event event;
}
