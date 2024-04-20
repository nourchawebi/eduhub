package org.cloudcraft.coursemanagementservice.service;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.dto.*;
import org.cloudcraft.coursemanagementservice.exception.DuplicateValueException;
import org.cloudcraft.coursemanagementservice.exception.ResourceNotFoundException;
import org.cloudcraft.coursemanagementservice.module.*;
import org.cloudcraft.coursemanagementservice.repository.CourseRepo;
import org.cloudcraft.coursemanagementservice.serviceInt.ChapterServiceInt;
import org.cloudcraft.coursemanagementservice.serviceInt.CourseServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CourseService implements CourseServiceInt {
    @Resource
    private CourseRepo courseRepo;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private FileEntityService fileEntityService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private SummaryService summaryService;


    public Course addCourse(CourseRequest courseRequest) {
        if (courseRepo.findCourseByName(courseRequest.getName()).isPresent()) {
            throw new DuplicateValueException("course", "name", courseRequest.getName());
        }
        Course course = PayloadSerialization.getCourseFromCourseRequest(courseRequest);
        if (courseRequest.getImage() != null) {
            FileEntity savedFileEntity = fileEntityService.saveFileEntity(courseRequest.getImage());
            course.setImage(savedFileEntity);
        }


        return courseRepo.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }


    public Course save(Course course) {
        return courseRepo.save(course);
    }


    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepo.findById(id);
        if (course.isPresent()) {
            return course.get();

        }
        throw new ResourceNotFoundException("course", id);

    }

    public boolean deleteChapterFromCourse(Long courseId, Long chapterId) {
        Course course = this.getCourseById(courseId);
        List<Chapter> chapters = course.getChapters();
        if (chapters == null || chapters.isEmpty()) return false;
        int index = 0;
        for (Chapter chapterItem : chapters) {
            // Assuming each item has a method getId() to retrieve its ID
            if (Objects.equals(chapterItem.getChapterId(), chapterId)) {
                break;
            }
            index++;
        }
        if (index > chapters.size() - 1) return false;
        chapters.remove(index);
        chapterService.deleteChapter(chapterId);
        System.out.println("////////////////////");
        System.out.println(course);
        Course c = this.save(course);
        System.out.println(c);
        System.out.println("good2");
        return true;
    }

    public void deleteCourse(Long courseId) {
        Course course = this.getCourseById(courseId);
        List<Chapter> chapters = course.getChapters();
        if (chapters != null && !chapters.isEmpty()) {
            for (Chapter chapterItem : chapters) {
                this.chapterService.deleteChapter(chapterItem.getChapterId());
            }

        }
        if (chapters != null) chapters.clear();
        List<Summary> summaries = course.getSummaries();
        if (summaries != null && !summaries.isEmpty()) {
            for (Summary summaryItem : summaries) {
                this.summaryService.deletSummaryById(summaryItem.getSummaryId());
            }

        }
        if (summaries != null) summaries.clear();



        courseRepo.deleteById(courseId);
    }


    public Course updateCourse(Long courseId, CourseRequest courseRequest) {
        if (this.courseRepo.findCourseByName(courseRequest.getName()).isPresent()) {
            throw new DuplicateValueException("course", "name", courseRequest.getName());
        }
        Course oldCourse = getCourseById(courseId);
        oldCourse.setName(courseRequest.getName());
        return courseRepo.save(oldCourse);
    }

    public List<Rating> getAllRatings(Long courseId) {
        Course course = getCourseById(courseId);
        return course.getRating();
    }

    @Transactional
    public Rating addRating(Long courseId, RatingPayload ratingPayload) {
        Course course = getCourseById(courseId);
        Rating savedRating = ratingService.addRating(ratingPayload);
        List<Rating> courseRatings = course.getRating();
        if (courseRatings == null) {
            courseRatings = new ArrayList<>();
            courseRatings.add(savedRating);
        } else {
            courseRatings.add(savedRating);
        }
        courseRepo.save(course);
        return savedRating;
    }


    public void affectChapter(Chapter chapter, Long courseId) {
        Course course = getCourseById(courseId);
        if (course.getChapters() == null) course.setChapters(new ArrayList<>());
        course.getChapters().add(chapter);
        courseRepo.save(course);
    }

    public boolean isCourseContainChapter(Long courseId, String chapterTitile) {
        List<Chapter> chapters = getCourseById(courseId).getChapters();
        System.out.println(chapters);
        if (chapters == null) return false;
        return chapters.stream().anyMatch(chapter -> chapter.getTitle().equals(chapterTitile));
    }

    public Chapter addChapterToCourse(ChapterRequest chapterRequest, Long courseId) {
        if (this.isCourseContainChapter(courseId, chapterRequest.getTitle()))
            throw new DuplicateValueException("chapter", "title", chapterRequest.getTitle());
        Chapter chapter = Chapter.builder()
                .title(chapterRequest.getTitle())
                .description(chapterRequest.getDescription())
                .build();
        Chapter savedChapter = chapterService.save(chapter);
        Course course = this.getCourseById(courseId);
        course.getChapters().add(savedChapter);
        this.save(course);
        return savedChapter;
    }

    public List<Chapter> getAllChaptersByCourse(Long courseId) {
        Course course = this.getCourseById(courseId);
        List<Chapter> chapters = course.getChapters();
        if (chapters == null) {
            String message = "there are no chapters for course with id " + chapters;
            throw new ResourceNotFoundException(message);
        }
        return chapters;

    }


    public boolean deletSummaryFromCourse(Long courseId,Long summaryId){
        Course course =this.getCourseById(courseId);
        int index = 0;
        List<Summary> summaries=course.getSummaries();
        if(summaries==null|| summaries.isEmpty()) {
            return false;
        }
        for (Summary summaryItem : summaries) {
            // Assuming each item has a method getId() to retrieve its ID
            if (Objects.equals(summaryItem.getSummaryId(), summaryId)) {
                break;
            }
            index++;
        }
        if(index > summaries.size()-1) return false;
        summaries.remove(index);
        this.save(course);
        summaryService.deletSummaryById(summaryId);
        return true;
    }
    public Summary addSummaryToCourse(Long courseId, SummaryRequest summaryRequest){
        Summary summary = PayloadSerialization.getSummaryFromSummaryRequest(summaryRequest);
        Course course=this.getCourseById(courseId);
        List<Summary> courseSummaries=course.getSummaries();
        if(courseSummaries==null) course.setSummaries(new ArrayList<>());
        if(summaryService.isSummaryExistInCourse(course,summary.getTitle())) throw new DuplicateValueException("summary","title",summary.getTitle());
        for(MultipartFile file:summaryRequest.getFiles()){
            FileEntity savedFileEntity =fileEntityService.saveFileEntity(file);
            if(summary.getFiles()==null) summary.setFiles(new ArrayList<>());
            summary.getFiles().add(savedFileEntity);
        }
        Summary savedSummary= summaryService.save(summary);
        course.getSummaries().add(savedSummary);
        this.save(course);
        return savedSummary;
    }
}









