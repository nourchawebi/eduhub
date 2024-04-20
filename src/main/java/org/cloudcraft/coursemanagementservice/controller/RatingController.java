package org.cloudcraft.coursemanagementservice.controller;


import org.cloudcraft.coursemanagementservice.dto.PayloadSerialization;
import org.cloudcraft.coursemanagementservice.dto.RatingPayload;
import org.cloudcraft.coursemanagementservice.module.Rating;
import org.cloudcraft.coursemanagementservice.serviceInt.RatingServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("ratings")
public class RatingController {
    @Autowired
    private RatingServiceInt ratingServiceInt;

    @PutMapping("{ratingId}")
    public ResponseEntity<RatingPayload> updateRating(@PathVariable Long ratingId, @RequestBody RatingPayload ratingPayload){
        return ResponseEntity.ok().body(PayloadSerialization.prepareRatingResponse(ratingServiceInt.updateRating(ratingId,ratingPayload)));
    }

    @DeleteMapping("{ratingId}")
    public ResponseEntity<Boolean> deleteRating(@PathVariable Long ratingId, @RequestBody RatingPayload ratingPayload){
        return ResponseEntity.ok().body(ratingServiceInt.deleteRating(ratingId));
    }

}
