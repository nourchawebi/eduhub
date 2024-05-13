package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.dto.userdto.registerRequest;
import com.esprit.cloudcraft.entities.userEntities.ClassType;
import com.esprit.cloudcraft.entities.userEntities.RoleType;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.userServices.TesseractOCRService;
import com.esprit.cloudcraft.services.userServices.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
public class OcrController {
    @Resource
    private UserService userService;

    @Resource
    private TesseractOCRService tesseractOCRService;

    @PostMapping("/register/ocr")
    public ResponseEntity<?> recognizeText(@RequestParam("file") MultipartFile file) throws IOException {
        String ocrResult = tesseractOCRService.recognizeText(file.getInputStream());

        // Extract information from OCR result
        String[] lines = ocrResult.split("\\r?\\n");
        String firstName = extractValue(lines, "NOM");
        String lastName = extractValue(lines, "PRENOM");
        String email = extractEmail(lines);
        ClassType classType = extractClassType(lines);

        // Create and populate the registerRequest object

        User request  = new User();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setPassword(email);
        request.setClassType(classType);
        request.setRole(RoleType.USER); // Set the role as needed
        request.setEnable(true); // Enable the user by default
        request.setBirthDate(new Date());
        boolean test=userService.findByEmail(request.getEmail());
        if (userService.findByEmail(request.getEmail()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(test);
        }


        MultipartFile image= null;
        var response=userService.register(request,image);
        return ResponseEntity.accepted().build();
    }


    // Helper method to extract value based on keyword

    private String extractValue(String[] lines, String keyword) {
        for (String line : lines) {
            if (line.contains(keyword)) {
                String value = line.substring(line.indexOf(":") + 1).trim(); // Start from after the colon
                // Filter out non-letter characters
                value = value.replaceAll("[^a-zA-Z\\s]", "");
                // Trim trailing spaces and end the value if there are more than 2 consecutive spaces
                value = value.replaceAll("\\s{3,}.*", "").trim();
                return value;
            }
        }
        return null;
    }



    // Helper method to extract email
    private String extractEmail(String[] lines) {
        for (String line : lines) {
            if (line.contains("IDENTIFIANT")) {
                return line.substring(line.indexOf(":") + 2, line.indexOf(":") + 12);
            }
        }
        return null;
    }

    // Helper method to extract class type
    private ClassType extractClassType(String[] lines) {
        for (String line : lines) {
            if (line.contains("CLASSE")) {
                String classTypeCodeStr = line.substring(line.indexOf(":") + 2, line.indexOf("ARCTIC") - 1).trim();
                if (!classTypeCodeStr.isEmpty()) {
                    int classTypeCode = Integer.parseInt(classTypeCodeStr);
                    // Assuming ClassType enum has values like FIRSTYEAR, SECONDYEAR, etc.
                    switch (classTypeCode) {
                        case 1:
                            return ClassType.FIRSTYEAR;
                        case 2:
                            return ClassType.SECONDYEAR;
                        case 3:
                            return ClassType.THIRDYEAR;
                        case 4:
                            return ClassType.FOURTHYEAR;
                        default:
                            return null;
                    }
                } else {
                    return null; // Handle empty class type code
                }
            }
        }
        return null; // Handle if "CLASSE" keyword not found
    }


}