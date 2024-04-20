package org.cloudcraft.coursemanagementservice.service;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.dto.PayloadSerialization;
import org.cloudcraft.coursemanagementservice.dto.SummaryRequest;
import org.cloudcraft.coursemanagementservice.exception.DuplicateValueException;
import org.cloudcraft.coursemanagementservice.exception.ResourceNotFoundException;
import org.cloudcraft.coursemanagementservice.module.*;
import org.cloudcraft.coursemanagementservice.repository.SummaryRepo;
import org.cloudcraft.coursemanagementservice.serviceInt.ChapterServiceInt;
import org.cloudcraft.coursemanagementservice.serviceInt.ContentServiceInt;
import org.cloudcraft.coursemanagementservice.serviceInt.CourseServiceInt;
import org.cloudcraft.coursemanagementservice.serviceInt.SummaryServiceInt;
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


    public Summary getSummaryById(Long summaryId){
        Summary summary= this.summaryRepo.findById(summaryId).orElse(null);
        if(summary==null) throw new ResourceNotFoundException("summary",summaryId);
        return summary;
    }



    public Summary save(SummaryRequest summaryRequest){
        Summary summary = PayloadSerialization.getSummaryFromSummaryRequest(summaryRequest);

        return summaryRepo.save(summary);
    }
    public Summary save(Summary summary){
        return summaryRepo.save(summary);
    }
    public boolean deletSummaryById(Long summaryId){
        //DELETE FILES
        summaryRepo.deleteById(summaryId);
        return true;
    }

    public Summary addSummaryToChapter(Long chapterId, SummaryRequest summaryRequest){
        Summary summary = PayloadSerialization.getSummaryFromSummaryRequest(summaryRequest);
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


    public boolean isSummaryExistInCourse(Course course,String summaryTitle){
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


}
