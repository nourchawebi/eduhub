package com.event.test.InterfaceService;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String saveImage(MultipartFile image);
}
