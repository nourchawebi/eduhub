package org.cloudcraft.coursemanagementservice.serviceInt;

import org.cloudcraft.coursemanagementservice.dto.SummaryRequest;
import org.cloudcraft.coursemanagementservice.module.Summary;

import java.util.List;

public interface SummaryServiceInt {
    public Summary getSummaryById(Long summaryId);

    public List<Summary> getSummariesByCourse(Long courseId);
    public Summary addSummaryToChapter(Long chapterId, SummaryRequest summaryRequest);
    public List<Summary>  getSummariesByChapter(Long chapterId);
    public boolean deletSummaryById(Long summaryId);

    public boolean deletSummaryFromChapter(Long summaryId,Long chapterId);

    public boolean deletSummaryFromCourse(Long courseId,Long summaryId);


}
