package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.User;

public interface EmailWithAttachmentService {
    void sendEmailWithAttachment(User user, BookLoan bookLoan);

}
