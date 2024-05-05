package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.ChapterRequest;
import com.esprit.cloudcraft.entities.*;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.exceptions.UnauthorizedActionException;
import com.esprit.cloudcraft.services.userServices.UserService;
import com.esprit.cloudcraft.upload.FileUploadService;
import jakarta.annotation.Resource;
import com.esprit.cloudcraft.dto.ContentRequest;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.exceptions.ResourceNotFoundException;
import com.esprit.cloudcraft.repository.ChapterRepo;
import com.esprit.cloudcraft.serviceInt.ChapterServiceInt;
import com.esprit.cloudcraft.serviceInt.RatingServiceInt;
import com.esprit.cloudcraft.upload.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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
    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;








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
        User connectedUser=userService.getConnectedUser();
        if(chapter.getOwner().getEmail()!=connectedUser.getEmail()){
            throw new UnauthorizedActionException("You can not delete chapter that you dont own");
        }
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


    public Boolean deleteChapterAdmin(Long chapterId){
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



    public Chapter updateChapter(Long chapterID, ChapterRequest chapterRequest){
        Chapter chapter=getChapterById(chapterID);
        User connectedUser=userService.getConnectedUser();
        if(chapter.getOwner().getEmail()!=connectedUser.getEmail()){
            throw new UnauthorizedActionException("You can not modify chapter that you dont own");
        }
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
        User connectedUser=userService.getConnectedUser();
        Content content=contentService.saveContent(contentRequest,connectedUser);
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
        User connectedUser=userService.getConnectedUser();
        if(chapter.getChapterContent().get(index).getOwner().getEmail()!=connectedUser.getEmail()){
            throw new UnauthorizedActionException("You can not delete chapter content that you dont own");
        }



        contents.remove(index);
        chapterRepo.save(chapter);
        contentService.deleteContentById(contentId);
        return true;
    }
    public Rating addRatingToChapter(Long chapterId, RatingPayload ratingPayload){
        Chapter chapter=getChapterById(chapterId);
        User connectedUser=userService.getConnectedUser();
        List<Rating> chapterRatings=chapter.getRatings();
        if (chapterRatings!=null) {

            // Access more details from UserDetails object
            if(chapterRatings.stream().anyMatch(rating -> rating.getOwner().getEmail()==connectedUser.getUsername())){
                throw new UnauthorizedActionException("you can not add more than one rating to the same chapter");
            }
        }


        Rating savedRating=ratingServiceInt.addRating(ratingPayload,connectedUser);
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

    public boolean deleteRatingFromChapter(Long chapterid,Long ratingId){
        Rating rating =ratingService.getRatingById(ratingId);
        User connectedUser=userService.getConnectedUser();
        if(rating.getOwner().getEmail()!=connectedUser.getEmail()){
            throw new UnauthorizedActionException("you can not delete rating that you dont own");
        }

        Chapter chapter=getChapterById(chapterid);
        chapter.setRatings(chapter.getRatings().stream().filter(rating1 -> rating1.getRatingId()!=ratingId).toList());
        chapterRepo.save(chapter);
        ratingService.deleteRating(ratingId);
        return true;
    }


}
