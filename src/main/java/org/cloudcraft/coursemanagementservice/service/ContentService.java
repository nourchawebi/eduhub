package org.cloudcraft.coursemanagementservice.service;


import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import org.cloudcraft.coursemanagementservice.dto.ContentRequest;
import org.cloudcraft.coursemanagementservice.dto.ContentResponse;
import org.cloudcraft.coursemanagementservice.exception.ResourceNotFoundException;
import org.cloudcraft.coursemanagementservice.module.Content;
import org.cloudcraft.coursemanagementservice.module.FileEntity;
import org.cloudcraft.coursemanagementservice.repository.ContentRepo;
import org.cloudcraft.coursemanagementservice.serviceInt.ContentServiceInt;
import org.cloudcraft.coursemanagementservice.upload.FileObject;
import org.cloudcraft.coursemanagementservice.upload.FileUploadService;
import org.cloudcraft.coursemanagementservice.upload.IFileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService implements ContentServiceInt {
    @Resource
    private ContentRepo contentRepo;
    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private FileEntityService fileEntityService;


    public Content findContentById(Long contentId){
        Content content=contentRepo.findById(contentId).orElse(null);
        if(content==null) throw new ResourceNotFoundException("content",contentId);
        return content;
    }
    @Override
    public Content saveContent(ContentRequest contentRequest) {
        System.out.println(contentRequest);
        Content content=Content.builder()
                .contentCategory(contentRequest.getContentCategory())
                .contentDescription(contentRequest.getContentDescription())
                .contentTitle(contentRequest.getContentTitle())
                .build();
        content.setFiles(new ArrayList<>());
        List<MultipartFile> files=contentRequest.getFiles();
        for(MultipartFile file:files){
           FileEntity savedFileEntity=fileEntityService.saveFileEntity(file);
            content.getFiles().add(savedFileEntity);
        }
        return contentRepo.save(content);

    }

    public boolean deleteContentById(Long contentId){
        Content content=findContentById(contentId);
        List<FileEntity> files= content.getFiles();
        for(FileEntity file:files){
            fileUploadService.deleteFileByName(file.getFileName(),file.getFileType());
        }
        contentRepo.deleteById(contentId);return true;
    }
    public Content updateContent(Long contentId,ContentRequest contentRequest){
        Content content =Content.builder()
                .contentTitle(contentRequest.getContentTitle())
                .contentCategory(contentRequest.getContentCategory())
                .build();
        return contentRepo.save(content);
    }


}
