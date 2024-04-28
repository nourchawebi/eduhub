package com.esprit.cloudcraft.implement;
import com.esprit.cloudcraft.entities.BookLoan;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.PdfGeneratorService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
@Service
public class PdfGeneratorServiceImp implements PdfGeneratorService {

    @Override
    public void generatePdf(String filePath, BookLoan bookLoan, User user) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            // Ajouter les informations du BookLoan
            document.add(new Paragraph("Informations du BookLoan :"));
            document.add(new Paragraph("ID BookLoan : " + bookLoan.getIdBookLoan()));
            document.add(new Paragraph("Date d'emprunt : " + bookLoan.getLoanDate()));
            document.add(new Paragraph("Date d'échéance : " + bookLoan.getDueDate()));

            // Ajouter les informations de l'utilisateur associé
            document.add(new Paragraph("\nInformations de l'utilisateur :"));
            document.add(new Paragraph("ID Utilisateur : " + user.getId()));
            document.add(new Paragraph("Nom : " + user.getFirstName() + " " + user.getLastName()));
            document.add(new Paragraph("Email : " + user.getEmail()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "bookLoanDetails.pdf";
        //BookLoan bookLoan = // Récupérer le BookLoan depuis votre service ou DAO
                //generatePdf(filePath, bookLoan);
        System.out.println("PDF généré avec succès.");
    }
}
