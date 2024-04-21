package com.esprit.cloudcraft.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
     String saveFile( MultipartFile sourceFile, Long bookId, Long userId);
     String uploadFile(MultipartFile sourceFile, String fileUploadSubPath);
     String getFileExtension(String fileName);
     String saveImage(MultipartFile image);
}
