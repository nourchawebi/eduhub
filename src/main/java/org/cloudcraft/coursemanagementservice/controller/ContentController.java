package org.cloudcraft.coursemanagementservice.controller;


import org.cloudcraft.coursemanagementservice.dto.ContentRequest;
import org.cloudcraft.coursemanagementservice.dto.ContentResponse;
import org.cloudcraft.coursemanagementservice.dto.PayloadSerialization;
import org.cloudcraft.coursemanagementservice.serviceInt.ChapterServiceInt;
import org.cloudcraft.coursemanagementservice.serviceInt.ContentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contents")
public class ContentController {
    @Autowired
    private ContentServiceInt contentServiceInt;



    @PutMapping("{contentId}")
    public ResponseEntity<ContentResponse> updateContent(@RequestBody ContentRequest contentRequest,@PathVariable Long contentId){
        return ResponseEntity.ok().body(PayloadSerialization.prepareContentResponse(contentServiceInt.updateContent(contentId,contentRequest)));
    }
}
