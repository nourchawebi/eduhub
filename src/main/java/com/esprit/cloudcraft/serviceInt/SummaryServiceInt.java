package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.module.Summary;
import com.esprit.cloudcraft.dto.SummaryRequest;

import java.util.List;

public interface SummaryServiceInt {
    public Summary getSummaryById(Long summaryId);

    public List<Summary> getSummariesByCourse(Long courseId);
    public Summary addSummaryToChapter(Long chapterId, SummaryRequest summaryRequest);
    public List<Summary>  getSummariesByChapter(Long chapterId);
    public boolean deletSummaryById(Long summaryId);

    public boolean deletSummaryFromChapter(Long summaryId,Long chapterId);

    public boolean deletSummaryFromCourse(Long courseId,Long summaryId);
    public boolean addRatingToSummary(Long summaryId, RatingPayload ratingPayload);


}
