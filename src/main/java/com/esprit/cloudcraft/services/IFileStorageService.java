package com.esprit.cloudcraft.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IFileStorageService {
    String saveImage(MultipartFile image);
    byte[] getPicture(String pictureUrl);
    Path getImagePath(String fileName);
}