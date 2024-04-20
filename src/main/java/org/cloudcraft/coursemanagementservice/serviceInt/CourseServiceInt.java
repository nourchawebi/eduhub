package org.cloudcraft.coursemanagementservice.serviceInt;

import org.cloudcraft.coursemanagementservice.dto.*;
import org.cloudcraft.coursemanagementservice.module.Chapter;
import org.cloudcraft.coursemanagementservice.module.Course;
import org.cloudcraft.coursemanagementservice.module.Rating;
import org.cloudcraft.coursemanagementservice.module.Summary;

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
