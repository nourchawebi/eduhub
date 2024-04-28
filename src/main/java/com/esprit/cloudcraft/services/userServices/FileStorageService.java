package com.esprit.cloudcraft.services.userServices;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveImage(MultipartFile image);

}