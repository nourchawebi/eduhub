package com.esprit.cloudcraft.implement;
import com.esprit.cloudcraft.services.FileStorageService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.FileObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

//@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImp implements FileStorageService {
    @Value("${application.file.uploads.photos-output-path}")
    private String fileUploadPath;
    Path imagePath=Paths.get("uploads/images");


    @Override
    public String saveImage(MultipartFile image)  {
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String randomName = RandomStringUtils.randomAlphanumeric(10) + extension;

        try {
            Files.copy(
                    image.getInputStream(),
                    imagePath.resolve(randomName)
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return randomName;
    }


}
