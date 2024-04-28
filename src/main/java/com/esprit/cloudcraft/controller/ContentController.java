package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.ContentResponse;
import com.esprit.cloudcraft.dto.PayloadSerialization;
import com.esprit.cloudcraft.serviceInt.ContentServiceInt;
import com.esprit.cloudcraft.dto.ContentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contents")
public class ContentController {
    @Autowired
    private ContentServiceInt contentServiceInt;



    @PutMapping("{contentId}")
    public ResponseEntity<ContentResponse> updateContent(@RequestBody ContentRequest contentRequest, @PathVariable Long contentId){
        return ResponseEntity.ok().body(PayloadSerialization.prepareContentResponse(contentServiceInt.updateContent(contentId,contentRequest)));
    }
}
