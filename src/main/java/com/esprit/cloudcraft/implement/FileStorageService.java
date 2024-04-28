package com.esprit.cloudcraft.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService implements com.esprit.cloudcraft.services.userServices.FileStorageService {

    @Value("${application.file.uploads.photos-output-path}")
    private String fileUploadPath;
    Path imagePath=Paths.get("uploads/images");


    @Override
    public String saveImage(MultipartFile image)  {
        try {
            Files.copy(
                    image.getInputStream(),
                    imagePath.resolve(image.getOriginalFilename())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image.getOriginalFilename();
    }


}
