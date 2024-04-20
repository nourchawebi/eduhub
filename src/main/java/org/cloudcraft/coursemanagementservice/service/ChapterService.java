package org.cloudcraft.coursemanagementservice.service;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.dto.ChapterRequest;
import org.cloudcraft.coursemanagementservice.dto.ContentRequest;
import org.cloudcraft.coursemanagementservice.dto.PayloadSerialization;
import org.cloudcraft.coursemanagementservice.dto.RatingPayload;
import org.cloudcraft.coursemanagementservice.exception.DuplicateValueException;
import org.cloudcraft.coursemanagementservice.exception.ResourceNotFoundException;
import org.cloudcraft.coursemanagementservice.module.*;
import org.cloudcraft.coursemanagementservice.repository.ChapterRepo;
import org.cloudcraft.coursemanagementservice.serviceInt.ChapterServiceInt;
import org.cloudcraft.coursemanagementservice.serviceInt.RatingServiceInt;
import org.cloudcraft.coursemanagementservice.upload.FileType;
import org.cloudcraft.coursemanagementservice.upload.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ChapterService implements ChapterServiceInt {
    @Resource
    private ChapterRepo chapterRepo;
    @Resource
    private ContentService contentService;


    @Resource
    private RatingServiceInt ratingServiceInt;
    @Autowired
    private FileUploadService fileUploadService;






    public Chapter getChapterById(Long chapterId){
        Chapter chapter =chapterRepo.findById(chapterId).orElse(null);
        if(chapter==null) throw new ResourceNotFoundException("chapter",chapterId);
        chapter.setChapterContent(chapter.getChapterContent());
        chapter.setRatings(chapter.getRatings());
        return chapter;
    }

    public Chapter save(Chapter chapter){
        return chapterRepo.save(chapter);
    }

    @Transactional


    public Boolean deleteChapter(Long chapterId){
        Chapter chapter=getChapterById(chapterId);
        List<String> fileNames=new ArrayList<>();
        for(Content content:chapter.getChapterContent()){
            fileNames.addAll(content.getFiles().stream().map(FileEntity::getFileName).toList());
        }
        for(Summary summary:chapter.getSummaries()){
            fileNames.addAll(summary.getFiles().stream().map(FileEntity::getFileName).toList());
        }
        for(String fileName:fileNames){
            fileUploadService.deleteFileByName(fileName, FileType.PDF);
        }

        chapterRepo.deleteById(chapterId);
        System.out.println("good1");
        return true;
    }






    public Chapter updateChapter(Long chapterID,ChapterRequest chapterRequest){
        Chapter chapter=getChapterById(chapterID);
        chapter.setDescription(chapterRequest.getDescription());
        chapter.setTitle(chapterRequest.getTitle());
        return chapterRepo.save(chapter);
    }

    public Content addContentToChapter(Long chapterId,ContentRequest contentRequest) {
        Chapter chapter=getChapterById(chapterId);
        List<Content> contents=chapter.getChapterContent();
        if(contents==null) {
            contents=new ArrayList<>();
        }
        Content content=contentService.saveContent(contentRequest);
        contents.add(content);

        Chapter savedChapter= chapterRepo.save(chapter);
        return content;
    }
    public boolean deleteContentFromChapter(Long chapterId,Long contentId){
        Chapter chapter =getChapterById(chapterId);
        System.out.println(chapter);
        int index = 0;
        List<Content> contents=chapter.getChapterContent();
        if(contents==null|| contents.isEmpty()) return false;
        for (Content contentItem : contents) {
            // Assuming each item has a method getId() to retrieve its ID
            if (Objects.equals(contentItem.getContentId(), contentId)) {
                break;
            }
            index++;
        }
     if(index>contents.size()-1) return false;
        contents.remove(index);
        chapterRepo.save(chapter);
        contentService.deleteContentById(contentId);
        return true;
    }
    public Rating addRatingToChapter(Long chapterId, RatingPayload ratingPayload){
        Chapter chapter=getChapterById(chapterId);
        Rating savedRating=ratingServiceInt.addRating(ratingPayload);
        List<Rating> chapterRatings=chapter.getRatings();
        if(chapterRatings==null) chapterRatings=new ArrayList<>();
        chapterRatings.add(savedRating);
        chapterRepo.save(chapter);
        return savedRating;
    }
    public List<Rating> getChapterRatings(Long chapterId){
        Chapter chapter=getChapterById(chapterId);
        return chapter.getRatings();
    }

//
//        chapter.setChapterContent(new ArrayList<>());
//        try{
//        Arrays.stream(chapterRequest.getPdfFiles()).forEach(file-> {
//            Content content= contentService.saveContentFromPdfFile(file);
//
//            chapter.getChapterContent().add(content);
//
//        });
//        Chapter savedChapter= chapterRepo.save(chapter);
//        courseService.affectChapter(chapter,courseId);
//        return savedChapter;
//    }catch(Exception e){
//        throw new RuntimeException("error uploading files,error :"+e.getMessage());
//    }
    public List<Content> getContents(Long chapterId){
        Chapter chapter=getChapterById(chapterId);
        return chapter.getChapterContent();
    }




}
