package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.EmailWithAttachmentService;
import com.itextpdf.layout.element.Table;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import java.io.InputStream;

@Service
public class EmailWithAttachmentServiceImp implements EmailWithAttachmentService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public void sendEmailWithAttachment(User user, BookLoan bookLoan) {
        String recipientAddress = user.getEmail();
        String subject = "Details of your Book Borrow";

        // Créer le contexte Thymeleaf et ajouter les objets utilisateur et bookLoan
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("bookLoan", bookLoan);

        // Rendre le contenu du template avec Thymeleaf
        String emailContent = templateEngine.process("book-borrow-template", context);

        try {
            // Générer le contenu du PDF avec iTextPDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document pdfContent = new Document(pdfDocument);
            // Ajouter les informations de l'utilisateur associé
            pdfContent.add(new Paragraph("\nUser Informations :"));
            pdfContent.add(new Paragraph("User Name : " + user.getFirstName() + " " + user.getLastName()));
            pdfContent.add(new Paragraph("Email : " + user.getEmail()));
            //Detail du book loan
            pdfContent.add(new Paragraph("Information of the Book Borrow :"));
            pdfContent.add(new Paragraph("ID BookLoan : " + bookLoan.getIdBookLoan()));
            pdfContent.add(new Paragraph("Borrow date : " + bookLoan.getLoanDate()));
            pdfContent.add(new Paragraph("Due date : " + bookLoan.getDueDate()));
            Table table = new Table(3);
            table.setWidthPercent(100);
            table.addCell("Book Reference");
            table.addCell("Title");
            table.addCell("Author");
            table.addCell(String.valueOf(bookLoan.getBook().getIdBook()));
            table.addCell(bookLoan.getBook().getTitle());
            table.addCell(bookLoan.getBook().getAuthor());
            pdfContent.add(table);

            pdfContent.close();

            InputStreamSource inputStreamSource = new InputStreamSource() {
                @Override
                public InputStream getInputStream() {
                    return new ByteArrayInputStream(outputStream.toByteArray());
                }
            };

            // Créer le message MIME pour l'e-mail
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientAddress);
            helper.setFrom("asma.choueibi@gmail.com");
            helper.setSubject(subject);
            // Utiliser le contenu de la template comme corps de l'e-mail
            helper.setText(emailContent, true);
            helper.addAttachment("bookBorrow.pdf", inputStreamSource);
            // Envoyer l'e-mail
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void SendEmail(String to, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("asma.choueibi@gmail.com");
        msg.setTo(to);
        msg.setText(body);
        msg.setSubject(subject);
        javaMailSender.send(msg);
    }



}