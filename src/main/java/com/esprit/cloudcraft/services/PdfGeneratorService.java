package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;


public interface PdfGeneratorService {
     void generatePdf(String filePath, BookLoan bookLoan, User user);
}
