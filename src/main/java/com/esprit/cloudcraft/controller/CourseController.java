package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.*;
import com.esprit.cloudcraft.entities.Course;
import com.esprit.cloudcraft.serviceInt.CourseServiceInt;
import com.esprit.cloudcraft.serviceInt.RatingServiceInt;
import com.esprit.cloudcraft.serviceInt.SummaryServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseServiceInt courseServiceInt;
    @Autowired
    private SummaryServiceInt summaryServiceInt;




    @PostMapping
    public ResponseEntity<CourseResponse>  createCourse(@RequestParam String name, @RequestParam MultipartFile image, @RequestParam String description){
        CourseRequest courseRequest=CourseRequest.builder()
                .name(name)
                .description(description)
                .image(image)
                .build();
        System.out.println(courseRequest);
        Course newCourse=courseServiceInt.addCourse(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepeareCourseResponse(newCourse));
    }
    @GetMapping
    public ResponseEntity<List<CourseDetailedResponse>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(PayloadSerialization.prepeareListCoursePayload(courseServiceInt.getAllCourses()));
    }
    @GetMapping("{courseId}")
    public ResponseEntity<CourseDetailedResponse> getCourseById(@PathVariable("courseId") Long id){
        System.out.println(PayloadSerialization.prepeareCourseDetailedResponse(courseServiceInt.getCourseById(id)));
        return ResponseEntity.status(HttpStatus.OK).body( PayloadSerialization.prepeareCourseDetailedResponse(courseServiceInt.getCourseById(id)));
    }
    @DeleteMapping("{courseId}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long courseId){
        courseServiceInt.deleteCourse(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
    @PutMapping("{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest courseRequest
    ){
        return ResponseEntity.status(HttpStatus.OK).body(PayloadSerialization.prepeareCourseResponse(courseServiceInt.updateCourse(courseId,courseRequest)));
    }

    //course rating functionalities
    @GetMapping("{courseId}/ratings")
    public ResponseEntity<List<RatingPayload>> getCourseRatings(@PathVariable Long courseId){
        return ResponseEntity.status(HttpStatus.OK).body(PayloadSerialization.prepareRatingResponselist(courseServiceInt.getAllRatings(courseId)));
    }


    @PostMapping(value="{courseId}/summaries")
    public ResponseEntity<SummaryResponse> addSummaryToCourse(@PathVariable Long courseId,@RequestParam String title, @RequestParam List<MultipartFile> files, @RequestParam String description){
        SummaryRequest summaryRequest=SummaryRequest.builder()
                .title(title)
                .description(description)
                .files(files)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareSummaryResponse(courseServiceInt.addSummaryToCourse(courseId,summaryRequest)));
    }
    @GetMapping(value="{courseId}/summaries")
    public ResponseEntity<List<SummaryResponse>> addSummaryToCourse(@PathVariable Long courseId){
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareSummaryResponseList(summaryServiceInt.getSummariesByCourse(courseId)));
    }

    @DeleteMapping(value="{courseId}/summaries/{summaryId}")
    public ResponseEntity<Boolean> deleteSummaryFromCourse(@PathVariable Long courseId,@PathVariable Long summaryId){
        return ResponseEntity.status(HttpStatus.OK).body(summaryServiceInt.deletSummaryFromCourse(courseId,summaryId));
    }




    @DeleteMapping(value="{courseId}/ratings/{ratingId}")
    public ResponseEntity<Boolean> deleteRatingFromCourse(@PathVariable Long courseId,@PathVariable Long ratingId){
        return ResponseEntity.status(HttpStatus.OK).body(courseServiceInt.deletSummaryFromCourse(courseId,ratingId));
    }






}
