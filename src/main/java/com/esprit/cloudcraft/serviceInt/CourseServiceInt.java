package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.ChapterRequest;
import com.esprit.cloudcraft.dto.CourseRequest;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.dto.SummaryRequest;
import com.esprit.cloudcraft.entities.Chapter;
import com.esprit.cloudcraft.entities.Course;
import com.esprit.cloudcraft.entities.Rating;
import com.esprit.cloudcraft.entities.Summary;

import java.util.List;

public interface  CourseServiceInt {

    public Course addCourse(CourseRequest course);
    public Course save(Course course);
    public List<Course> getAllCourses();

    public Course getCourseById(Long courseId);
    public void deleteCourse(Long courseId);

    public Course updateCourse(Long courseId, CourseRequest course);

    public List<Rating> getAllRatings(Long courseId);
    public Rating addRating(Long courseId, RatingPayload rating );
    public boolean deleteChapterFromCourse(Long courseId,Long chapterId);
    public boolean deletSummaryFromCourse(Long courseId,Long summaryId);
    public List<Chapter> getAllChaptersByCourse(Long courseId);
    public Chapter addChapterToCourse(ChapterRequest chapterRequest, Long courseId);
    public Summary addSummaryToCourse(Long courseId, SummaryRequest summaryRequest);



}
