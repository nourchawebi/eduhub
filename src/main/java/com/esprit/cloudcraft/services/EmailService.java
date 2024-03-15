package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.AbstractEmailContext;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMail(AbstractEmailContext email)throws MessagingException;
}
