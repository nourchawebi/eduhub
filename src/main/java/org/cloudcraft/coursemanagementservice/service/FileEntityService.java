package org.cloudcraft.coursemanagementservice.service;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.module.FileEntity;
import org.cloudcraft.coursemanagementservice.repository.FileEntityRepo;
import org.cloudcraft.coursemanagementservice.serviceInt.FileEntityServiceInt;
import org.cloudcraft.coursemanagementservice.upload.FileObject;
import org.cloudcraft.coursemanagementservice.upload.FileType;
import org.cloudcraft.coursemanagementservice.upload.FileUploadService;
import org.cloudcraft.coursemanagementservice.upload.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileEntityService implements FileEntityServiceInt {
    @Resource
    private FileEntityRepo fileEntityRepo;
    @Autowired
    private FileUploadService fileUploadService;
    private final String baseUrl="http://localhost:8080/";
    public FileEntity saveFileEntity(FileEntity fileEntity){
        return fileEntityRepo.save(fileEntity);
    }

    public FileEntity saveFileEntity(MultipartFile file){
        FileObject fileObject= fileUploadService.save(file);
        FileEntity fileEntity= FileEntity.builder()
                .fileName(fileObject.getFileName())
                .fileLocation(fileObject.getFileLocation().toString())
                .build();
        if(fileObject.getFileType()== FileType.PDF) fileEntity.setUrl(baseUrl+"files/"+fileObject.getFileName()+"?fileType=PDF");
        if(fileObject.getFileType()== FileType.IMAGE) fileEntity.setUrl(baseUrl+"files/"+fileObject.getFileName()+"?fileType=IMAGE");
        return saveFileEntity(fileEntity);

    }
}
