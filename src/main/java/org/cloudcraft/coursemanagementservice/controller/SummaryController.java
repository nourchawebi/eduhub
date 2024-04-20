package org.cloudcraft.coursemanagementservice.controller;


import org.cloudcraft.coursemanagementservice.dto.PayloadSerialization;
import org.cloudcraft.coursemanagementservice.dto.SummaryRequest;
import org.cloudcraft.coursemanagementservice.dto.SummaryResponse;
import org.cloudcraft.coursemanagementservice.module.Summary;
import org.cloudcraft.coursemanagementservice.serviceInt.SummaryServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//    @PutMapping("{summaryId}")
//    public ResponseEntity<Summary> updateSummary(@PathVariable Long summaryId, SummaryRequest summaryRequest){
//        return ResponseEntity.status(HttpStatus.OK).body(summaryServiceInt.deletSummaryById(summaryId));
//    }
    //DELETE FILES BEFORE ANY DELETING OF FILE CONTENT
    //UPDATE FILES (ADD FILE,REMOVE FILE)
    //SUMMARY IMAGES
    //SUMMARY(UPDATE,GET ONE)

}
