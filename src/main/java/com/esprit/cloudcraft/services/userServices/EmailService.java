package com.esprit.cloudcraft.services.userServices;

import com.esprit.cloudcraft.entities.userEntities.AbstractEmailContext;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMail(AbstractEmailContext email)throws MessagingException;
}
