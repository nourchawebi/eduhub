package com.esprit.cloudcraft.services;


import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.ContentServiceInt;
import com.esprit.cloudcraft.upload.FileUploadService;
import jakarta.annotation.Resource;
import com.esprit.cloudcraft.dto.ContentRequest;
import com.esprit.cloudcraft.exceptions.ResourceNotFoundException;
import com.esprit.cloudcraft.entities.Content;
import com.esprit.cloudcraft.entities.FileEntity;
import com.esprit.cloudcraft.repository.ContentRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Content saveContent(ContentRequest contentRequest, User user) {
        System.out.println(contentRequest);
        Content content=Content.builder()
                .contentCategory(contentRequest.getContentCategory())
                .contentDescription(contentRequest.getContentDescription())
                .owner(user)
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
