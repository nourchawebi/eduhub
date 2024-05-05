package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.*;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.exceptions.DuplicateValueException;
import com.esprit.cloudcraft.exceptions.ResourceNotFoundException;
import com.esprit.cloudcraft.entities.*;
import com.esprit.cloudcraft.exceptions.UnauthorizedActionException;
import com.esprit.cloudcraft.repository.CourseRepo;
import com.esprit.cloudcraft.serviceInt.CourseServiceInt;
import com.esprit.cloudcraft.services.userServices.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


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
    @Autowired
    private UserService userService;


    public Course addCourse(CourseRequest courseRequest) {
        if (courseRepo.findCourseByName(courseRequest.getName()).isPresent()) {
            throw new DuplicateValueException("course", "name", courseRequest.getName());
        }
        Course course = PayloadSerialization.getCourseFromCourseRequest(courseRequest);
        if (courseRequest.getImage() != null) {
            FileEntity savedFileEntity = fileEntityService.saveFileEntity(courseRequest.getImage());
            course.setImage(savedFileEntity);
        }

        User connectedUser=userService.getConnectedUser();
        course.setOwner(connectedUser);

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
                this.chapterService.deleteChapterAdmin(chapterItem.getChapterId());
            }

        }
        if (chapters != null) chapters.clear();
        List<Summary> summaries = course.getSummaries();
        if (summaries != null && !summaries.isEmpty()) {
            for (Summary summaryItem : summaries) {
                this.summaryService.deletSummaryByIdAdmin(summaryItem.getSummaryId());
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
        User connectedUser=userService.getConnectedUser();
        if(course.getRating().stream().anyMatch(rating -> rating.getOwner().getEmail()==connectedUser.getEmail())){
            throw new UnauthorizedActionException("you can not rate a course more than one time");
        }
        Rating savedRating = ratingService.addRating(ratingPayload,connectedUser);
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
        User connectedUser=userService.getConnectedUser();

        Chapter chapter = Chapter.builder()
                .title(chapterRequest.getTitle())
                .owner(connectedUser)
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



    @Transactional
    public boolean deleteSummaryFromCourse(Long courseId,Long summaryId){

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
        summaryService.deletSummaryById(summaryId);
        summaries.remove(index);
        this.save(course);
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
        User connectedUser=userService.getConnectedUser();
        summary.setOwner(connectedUser);
        Summary savedSummary= summaryService.save(summary);
        course.getSummaries().add(savedSummary);
        this.save(course);
        return savedSummary;
    }


    public boolean deleteRatingFromCourse(Long courseId,Long ratingId){
        System.out.println("REACHING HERE");
        Rating rating =ratingService.getRatingById(ratingId);
        System.out.println(rating);
        User connectedUser=userService.getConnectedUser();


        if(!Objects.equals(rating.getOwner().getEmail(), connectedUser.getEmail())){
            throw new UnauthorizedActionException("you can not delete rating that you dont own");
        }
        System.out.println(connectedUser);
        System.out.println(rating.getOwner());
        Course course=getCourseById(courseId);
        course.setRating(course.getRating().stream().filter(rating1 -> !Objects.equals(rating1.getRatingId(), ratingId)).collect(Collectors.toList()));
        courseRepo.save(course);
        ratingService.deleteRating(ratingId);
        return true;
    }
}









