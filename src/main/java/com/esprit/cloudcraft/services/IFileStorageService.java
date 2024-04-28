package com.esprit.cloudcraft.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String saveImage(MultipartFile image);
}
