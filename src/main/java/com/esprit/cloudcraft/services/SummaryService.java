package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.PayloadSerialization;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.exceptions.DuplicateValueException;
import com.esprit.cloudcraft.exceptions.ResourceNotFoundException;
import com.esprit.cloudcraft.entities.*;
import com.esprit.cloudcraft.exceptions.UnauthorizedActionException;
import com.esprit.cloudcraft.repository.SummaryRepo;
import com.esprit.cloudcraft.serviceInt.ChapterServiceInt;
import com.esprit.cloudcraft.dto.SummaryRequest;
import com.esprit.cloudcraft.serviceInt.SummaryServiceInt;
import com.esprit.cloudcraft.services.userServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SummaryService implements SummaryServiceInt {

    @Autowired
    private SummaryRepo summaryRepo;
    @Autowired
    private FileEntityService fileEntityService;
    @Autowired
    private ChapterServiceInt chapterServiceInt;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;


    public Summary getSummaryById(Long summaryId){


        Summary summary= this.summaryRepo.findById(summaryId).orElse(null);
        if(summary==null) throw new ResourceNotFoundException("summary",summaryId);
        return summary;
    }

    public Summary updateSummary(Long id,SummaryRequest summaryRequest){
        Summary summary=getSummaryById(id);
        summary.setDescription(summaryRequest.getDescription());
        summary.setTitle(summaryRequest.getTitle());
        return summaryRepo.save(summary);
    }



    public Summary save(SummaryRequest summaryRequest){
        Summary summary = PayloadSerialization.getSummaryFromSummaryRequest(summaryRequest);

        return summaryRepo.save(summary);
    }
    public Summary save(Summary summary){

        return summaryRepo.save(summary);
    }

    public Summary saveWith(Summary summary){
        return summaryRepo.save(summary);
    }
    public boolean deletSummaryById(Long summaryId){
        //DELETE FILES

        Summary summary=getSummaryById(summaryId);
        User connectedUser=userService.getConnectedUser();
        if(summary.getOwner().getEmail()!=connectedUser.getEmail()){
            throw new UnauthorizedActionException("you can not delete summary you dont own");
        }
        summaryRepo.deleteById(summaryId);
        return true;
    }


    public boolean deletSummaryByIdAdmin(Long summaryId){
        //DELETE FILES
        summaryRepo.deleteById(summaryId);
        return true;
    }

    public Summary addSummaryToChapter(Long chapterId, SummaryRequest summaryRequest){
        Summary summary = PayloadSerialization.getSummaryFromSummaryRequest(summaryRequest);

        User connectedUser=userService.getConnectedUser();
        summary.setOwner(connectedUser);
        Chapter chapter=chapterServiceInt.getChapterById(chapterId);
        List<Summary> chapterSummaries=chapter.getSummaries();
        if(chapterSummaries==null) chapter.setSummaries(new ArrayList<>());
        if(isSummaryExistInChapter(chapter,summary.getTitle())) throw new DuplicateValueException("Summary","title",summary.getTitle());
        summary.setFiles(new ArrayList<>());
        for(MultipartFile file:summaryRequest.getFiles()){
            FileEntity savedFileEntity =fileEntityService.saveFileEntity(file);
            summary.getFiles().add(savedFileEntity);
        }
        Summary savedSummary=summaryRepo.save(summary);
        chapter.getSummaries().add(savedSummary);
        chapterServiceInt.save(chapter);
        return savedSummary;
    }



    public List<Summary> getSummariesByCourse(Long courseId){
        return summaryRepo.findSummariesByCourseId(courseId);
    }
    public List<Summary> getSummariesByChapter(Long chapterId){
        Chapter chapter =chapterServiceInt.getChapterById(chapterId);
        return chapter.getSummaries();
    }


    public boolean isSummaryExistInCourse(Course course, String summaryTitle){
        return course
                .getSummaries()
                .stream()
                .anyMatch(
                        courseSummarie -> courseSummarie.getTitle().equals(summaryTitle)
                );
    }
    public boolean isSummaryExistInChapter(Chapter chapter,String summaryTitle){
        return chapter
                .getSummaries()
                .stream()
                .anyMatch(
                        chapterSummary -> chapterSummary.getTitle().equals(summaryTitle)
                );
    }
    public boolean deletSummaryFromChapter(Long summaryId,Long chapterId){
        Chapter chapter =chapterServiceInt.getChapterById(chapterId);
        System.out.println(chapter);
        int index = 0;
        List<Summary> summaries=chapter.getSummaries();
        if(summaries==null|| summaries.isEmpty()) {
            return false;
        }
        for (Summary summarieItem : summaries) {
            // Assuming each item has a method getId() to retrieve its ID
            if (Objects.equals(summarieItem.getSummaryId(), summaryId)) {
                break;
            }
            index++;
        }
        if(index > summaries.size()-1) return false;
        summaries.remove(index);
        chapterServiceInt.save(chapter);
        this.deletSummaryById(summaryId);
        return true;
    }

    @Override
    public boolean deletSummaryFromCourse(Long courseId, Long summaryId) {
        return false;
    }
    public boolean addRatingToSummary(Long summaryId, RatingPayload ratingPayload){
        Summary summary = getSummaryById(summaryId);
        User connectedUser=userService.getConnectedUser();
        if(summary.getRatings().stream().anyMatch(rating -> rating.getOwner().getEmail()==connectedUser.getEmail())){
            throw new UnauthorizedActionException("you can not rate a summary more than one time");
        }
        Rating savedRating = ratingService.addRating(ratingPayload,connectedUser);
        List<Rating> summaryratings = summary.getRatings();
        if (summaryratings == null) {
            summaryratings = new ArrayList<>();
            summaryratings.add(savedRating);
        } else {
            summaryratings.add(savedRating);
        }
        summaryRepo.save(summary);
        return true;
    }


    public boolean deleteRatingFromSummary(Long summaryId,Long ratingId){
       Rating rating =ratingService.getRatingById(ratingId);
        User connectedUser=userService.getConnectedUser();
        if(rating.getOwner().getEmail()!=connectedUser.getEmail()){
            throw new UnauthorizedActionException("you can not delete rating that you dont own");
        }

        Summary summary=getSummaryById(summaryId);
        summary.setRatings(summary.getRatings().stream().filter(rating1 -> rating1.getRatingId()!=ratingId).toList());
        summaryRepo.save(summary);
        ratingService.deleteRating(ratingId);
        return true;
    }


}


