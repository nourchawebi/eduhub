package org.example.coursemanagementservice.controller;


import org.example.coursemanagementservice.dto.*;
import org.example.coursemanagementservice.module.Chapter;
import org.example.coursemanagementservice.serviceInt.ChapterServiceInt;
import org.example.coursemanagementservice.serviceInt.CourseServiceInt;
import org.example.coursemanagementservice.serviceInt.RatingServiceInt;
import org.example.coursemanagementservice.serviceInt.SummaryServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("chapters")
public class ChapterController {
    @Autowired
    private ChapterServiceInt chapterServiceInt;
    @Autowired
    private RatingServiceInt ratingServiceInt;
    @Autowired
    private SummaryServiceInt summaryServiceInt;
    @Autowired
    private CourseServiceInt courseServiceInt;





    @GetMapping("/courses/{courseId}/chapters")
    public ResponseEntity<List<ChapterResponse>> getAllChaptersByCourse(@PathVariable Long courseId){
        List<Chapter> chapters= courseServiceInt.getAllChaptersByCourse(courseId);
        return ResponseEntity.status(HttpStatus.FOUND).body(PayloadSerialization.prepareChapterResponseList(chapters));
    }



    @PostMapping("/courses/{courseId}/chapters")
    public ResponseEntity<ChapterResponse> addChapter(@PathVariable Long courseId,@RequestBody ChapterRequest chapterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareChapterResponse(courseServiceInt.addChapterToCourse(chapterRequest,courseId)));
    }
    @DeleteMapping("/courses/{courseId}/chapters/{chapterId}")
    public ResponseEntity<Boolean> deleteChapterFromCourse(@PathVariable Long courseId,@PathVariable Long chapterId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseServiceInt.deleteChapterFromCourse(courseId,chapterId));
    }

    @GetMapping("chapters/{chapterId}")
    public ResponseEntity<ChapterDetailedResponse> getChapterById(@PathVariable Long chapterId){
        return ResponseEntity.status(HttpStatus.OK).body(PayloadSerialization.prepareChapterDetailedResponse(chapterServiceInt.getChapterById(chapterId)));

    }
    @PutMapping("chapters/{chapterId}")
    public ResponseEntity<ChapterResponse> updateChapter(@PathVariable Long chapterId,@RequestBody ChapterRequest chapterRequest){
        return ResponseEntity.status(HttpStatus.OK).body(PayloadSerialization.prepareChapterResponse(chapterServiceInt.updateChapter(chapterId,chapterRequest)));
    }
    @DeleteMapping("chapters/{chapterId}")
    public ResponseEntity<Boolean> deleteChapter(@PathVariable Long chapterId){
        return ResponseEntity.status(HttpStatus.GONE).body(chapterServiceInt.deleteChapter(chapterId));
    }


    @GetMapping("chapters/{chapterId}/contents")
    public ResponseEntity<List<ContentResponse>> getCapterContent(@PathVariable Long chapterId){
        return ResponseEntity.status(HttpStatus.OK).body(PayloadSerialization.prepareContentResponseList(chapterServiceInt.getContents(chapterId)));

    }
    @DeleteMapping("chapters/{chapterId}/contents/{contentId}")
    public ResponseEntity<Boolean> deleteContentFromChapterById(@PathVariable Long contentId,@PathVariable Long chapterId ){
        System.out.println("reached");
        return ResponseEntity.ok().body(chapterServiceInt.deleteContentFromChapter(chapterId,contentId));
    }

    @PostMapping(value = "chapters/{chapterId}/contents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContentResponse> addContentToChapter(ContentRequest contentRequest, @PathVariable Long chapterId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareContentResponse(chapterServiceInt.addContentToChapter(chapterId,contentRequest)));
    }

    @PostMapping("chapters/{chapterId}/ratings")
    public ResponseEntity<RatingPayload> rateChapter(@PathVariable Long chapterId, @RequestBody RatingPayload ratingPayload){
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareRatingResponse(chapterServiceInt.addRatingToChapter(chapterId,ratingPayload)));
    }
    @GetMapping("chapters/{chapterId}/ratings")
    public ResponseEntity<List<RatingPayload>> rateChapter(@PathVariable Long chapterId){
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareRatingResponselist(chapterServiceInt.getChapterRatings(chapterId)));
    }
    @PostMapping(value="chapters/{chapterId}/summaries",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SummaryResponse> addSummaryToCourse(@PathVariable Long chapterId,SummaryRequest summaryRequest){
        System.out.println(summaryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareSummaryResponse(summaryServiceInt.addSummaryToChapter(chapterId,summaryRequest)));
    }
    @GetMapping(value="chapters/{chapterId}/summaries")
    public ResponseEntity<List<SummaryResponse>> addSummaryToCourse(@PathVariable Long chapterId){
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareSummaryResponseList(summaryServiceInt.getSummariesByChapter(chapterId)));
    }
    @DeleteMapping("chapters/{chapterId}/summaries/{summaryId}")
    public ResponseEntity<Boolean> deleteSummaryFromChapter(@PathVariable Long summaryId,@PathVariable Long chapterId){
        return ResponseEntity.status(HttpStatus.OK).body(summaryServiceInt.deletSummaryFromChapter(summaryId,chapterId));
    }





//    @GetMapping("/getAllTest")
//    public ResponseEntity<Resource> getFileByName(String name){
//        Resource resource = iFileUploadService.getFileByName(name);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; fileName=\""
//                        +resource.getFilename()+"\"").body(resource);
//    }

//    public ResponseEntity<List<FileResponse>> getAllFiles(){
//        List<FileResponse> files=iFileUploadService.loadAllFiles()
//                .map(path->{
//                    String fileName = path.getFileName().toString();
//                    String url = MvcUriComponentsBuilder.fromMethodName(ChapterController.class,
//                            "getFileByName",path.getFileName().toString()).build().toString();
//                return new FileResponse(fileName,url);
//                }).toList();
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }







}
