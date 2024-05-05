package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.PayloadSerialization;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.dto.SummaryResponse;
import com.esprit.cloudcraft.serviceInt.SummaryServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/summaries")
public class SummaryController {
    @Autowired
    private SummaryServiceInt summaryServiceInt;

    @GetMapping(value="{summaryId}")
    public ResponseEntity<SummaryResponse> getSummary(@PathVariable Long summaryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(PayloadSerialization.prepareSummaryDetailedResponse(summaryServiceInt.getSummaryById(summaryId)));
    }
    @DeleteMapping("{summaryId}")
    public ResponseEntity<Boolean> deleteSummary(@PathVariable Long summaryId){
        return ResponseEntity.status(HttpStatus.OK).body(summaryServiceInt.deletSummaryById(summaryId));
    }


    @PostMapping("{summaryId}/ratings")
    public ResponseEntity<Boolean> addRatingToSummary(@PathVariable Long summaryId, @RequestBody RatingPayload ratingPayload){
        return ResponseEntity.status(HttpStatus.OK).body(summaryServiceInt.addRatingToSummary(summaryId,ratingPayload));
    }

//    @PutMapping("{summaryId}")
//    public ResponseEntity<Summary> updateSummary(@PathVariable Long summaryId, SummaryRequest summaryRequest){
//        return ResponseEntity.status(HttpStatus.OK).body(summaryServiceInt.deletSummaryById(summaryId));
//    }
    //DELETE FILES BEFORE ANY DELETING OF FILE CONTENT
    //UPDATE FILES (ADD FILE,REMOVE FILE)
    //SUMMARY IMAGES
    //SUMMARY(UPDATE,GET ONE)

}