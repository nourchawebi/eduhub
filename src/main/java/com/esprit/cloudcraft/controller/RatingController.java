package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.PayloadSerialization;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.serviceInt.RatingServiceInt;
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
