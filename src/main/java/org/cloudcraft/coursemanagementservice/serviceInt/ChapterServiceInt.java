package org.cloudcraft.coursemanagementservice.serviceInt;

import org.cloudcraft.coursemanagementservice.dto.ChapterRequest;
import org.cloudcraft.coursemanagementservice.dto.ContentRequest;
import org.cloudcraft.coursemanagementservice.dto.ContentResponse;
import org.cloudcraft.coursemanagementservice.dto.RatingPayload;
import org.cloudcraft.coursemanagementservice.module.Chapter;
import org.cloudcraft.coursemanagementservice.module.Content;
import org.cloudcraft.coursemanagementservice.module.Rating;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChapterServiceInt {


    public Chapter save(Chapter chapter);


    public Chapter updateChapter(Long chapterID,ChapterRequest chapterRequest);

    public Chapter getChapterById(Long chapterId);

    public Content addContentToChapter(Long chapterId,ContentRequest contentRequest);
    public List<Content> getContents(Long chapterId);

    public Boolean deleteChapter(Long chapterId);

    public Rating addRatingToChapter(Long chapterId, RatingPayload ratingPayload);
    public List<Rating> getChapterRatings(Long chapterId);
    public boolean deleteContentFromChapter(Long chapterId,Long contentId);


}
