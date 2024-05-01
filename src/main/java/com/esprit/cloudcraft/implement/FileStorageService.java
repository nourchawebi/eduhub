package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.services.IFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

import javax.tools.FileObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;
@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService implements IFileStorageService {
    private String fileUploadPath;
    Path imagePath=Paths.get("uploads/images");


    @Override
    public String saveImage(MultipartFile image)  {
        try {
            String randomFileName = UUID.randomUUID().toString();
            String originalFileName = image.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedFileName = randomFileName + fileExtension;
            Files.copy(image.getInputStream(), imagePath.resolve(savedFileName));

            return savedFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getPicture(String pictureUrl) {
        if (StringUtils.isBlank(pictureUrl)) {
            return null;
        }
        try {
            Path imagePath = this.imagePath.resolve(pictureUrl);
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Path getImagePath(String fileName){
        return this.imagePath.resolve(fileName);
    }
}
//    @Value("${application.file.uploads.photos-output-path}")
//    private String fileUploadPath;
//    Path imagePath=Paths.get("uploads/images");
//

//    @Override
//    public String saveImage(MultipartFile image)  {
//        try {
//            Files.copy(
//                    image.getInputStream(),
//                    imagePath.resolve(image.getOriginalFilename())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return image.getOriginalFilename();
//    }



