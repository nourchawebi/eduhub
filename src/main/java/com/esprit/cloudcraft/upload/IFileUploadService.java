package com.esprit.cloudcraft.upload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFileUploadService {
    public void init();

    public FileObject save(MultipartFile file);

    Resource getFileByName(String fileName);

    public void deleteAll();

    Stream<Path> loadAllFiles();
    public Path getFilePath(String fileName,FileType fileType);
    public void deleteFileByName(String fileName,FileType fileType);

}
