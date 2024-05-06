package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.EmailWithAttachmentService;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
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


import java.io.File;
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

        // Create the Thymeleaf context and add user and bookLoan objects
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("bookLoan", bookLoan);

        // Render the template content with Thymeleaf
        String emailContent = templateEngine.process("book-borrow-template", context);

        try {
            // Generate PDF content with iTextPDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document pdfContent = new Document(pdfDocument);

            // Load the images from the static folder
            File topImageFile = new File("src/main/resources/static/haut.png");
            File bottomImageFile = new File("src/main/resources/static/bas.png");

            // Insert the top image into the PDF document
            Image topImage = new Image(ImageDataFactory.create(topImageFile.getAbsolutePath()));
            topImage.setAutoScale(true); // Adjust image size to fit the page if needed
            pdfContent.add(topImage);

            // Add user information
            pdfContent.add(new Paragraph("\nPersonel Information :"));
            pdfContent.add(new Paragraph("Name : " + user.getFirstName() + " " + user.getLastName()));
            pdfContent.add(new Paragraph("Email : " + user.getEmail()));
            pdfContent.add(new Paragraph("\n_______________________________________________"));
            // Add book loan information
            pdfContent.add(new Paragraph("\nInformation of the Book Borrow :"));
            pdfContent.add(new Paragraph("ID Borrow : " + bookLoan.getIdBookLoan()));
            pdfContent.add(new Paragraph("Borrow date : " + bookLoan.getLoanDate()));
            pdfContent.add(new Paragraph("Due date : " + bookLoan.getDueDate()));
            // Add book information in a table
            Table table = new Table(3);
            table.setWidthPercent(100);
            table.addCell("Book Reference");
            table.addCell("Title");
            table.addCell("Author");
            table.addCell(String.valueOf(bookLoan.getBook().getIdBook()));
            table.addCell(bookLoan.getBook().getTitle());
            table.addCell(bookLoan.getBook().getAuthor());
            pdfContent.add(table);

            // Insert the bottom image into the PDF document
            Image bottomImage = new Image(ImageDataFactory.create(bottomImageFile.getAbsolutePath()));
            bottomImage.setWidth(900);
            bottomImage.setAutoScaleHeight(true);
            float marginTop = 842 - bottomImage.getImageScaledHeight() - 400; // A4 height - image height - margin
            bottomImage.setMarginTop(marginTop);
            pdfContent.add(bottomImage);
            pdfContent.close();

            InputStreamSource inputStreamSource = new InputStreamSource() {
                @Override
                public InputStream getInputStream() {
                    return new ByteArrayInputStream(outputStream.toByteArray());
                }
            };

            // Create MIME message for the email
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientAddress);
            helper.setFrom("nourelhoudachawebi@gmail.com");
            helper.setSubject(subject);
            // Use template content as email body
            helper.setText(emailContent, true);
            helper.addAttachment("bookBorrow.pdf", inputStreamSource);
            // Send the email
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void SendEmail(String to, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("nourelhoudachawebi@gmail.com");
        msg.setTo(to);
        msg.setText(body);
        msg.setSubject(subject);
        javaMailSender.send(msg);
    }



}