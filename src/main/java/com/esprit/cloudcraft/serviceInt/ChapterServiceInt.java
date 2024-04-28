package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.ChapterRequest;
import com.esprit.cloudcraft.dto.ContentRequest;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.entities.Chapter;
import com.esprit.cloudcraft.entities.Content;
import com.esprit.cloudcraft.entities.Rating;

import java.util.List;

public interface ChapterServiceInt {


    public Chapter save(Chapter chapter);


    public Chapter updateChapter(Long chapterID, ChapterRequest chapterRequest);

    public Chapter getChapterById(Long chapterId);

    public Content addContentToChapter(Long chapterId,ContentRequest contentRequest);
    public List<Content> getContents(Long chapterId);

    public Boolean deleteChapter(Long chapterId);

    public Rating addRatingToChapter(Long chapterId, RatingPayload ratingPayload);
    public List<Rating> getChapterRatings(Long chapterId);
    public boolean deleteContentFromChapter(Long chapterId,Long contentId);


}
