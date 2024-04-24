package com.esprit.cloudcraft.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
     String saveImage(MultipartFile image);
     byte[] getPicture(String pictureUrl);
}
