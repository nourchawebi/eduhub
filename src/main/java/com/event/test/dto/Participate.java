package com.event.test.dto;

import com.event.test.Entity.Event;
import com.event.test.Entity.User;
import lombok.Data;

@Data
public class Participate {
    private User user;
    private Event event;
}
