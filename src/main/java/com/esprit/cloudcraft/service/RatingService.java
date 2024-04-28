package com.esprit.cloudcraft.service;

import com.esprit.cloudcraft.dto.PayloadSerialization;
import com.esprit.cloudcraft.serviceInt.RatingServiceInt;
import jakarta.annotation.Resource;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.exception.ResourceNotFoundException;
import com.esprit.cloudcraft.module.Rating;
import com.esprit.cloudcraft.repository.RatingRepo;
import com.esprit.cloudcraft.utils.UtilFunctions;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements RatingServiceInt {
    @Resource
    private RatingRepo ratingRepo;

    public Rating addRating(RatingPayload ratingPayload) {
                    Rating newRating= PayloadSerialization.getRatingFromRatingPayload(ratingPayload);
                    newRating.setCreatedAt(UtilFunctions.getCurrentDateSql());
                    return ratingRepo.save(newRating);
                };




    public Rating updateRating(Long ratingId,RatingPayload ratingPayload ){
        Rating oldRating=ratingRepo.findById(ratingId).orElse(null);
        if(oldRating==null) throw new ResourceNotFoundException("rating",ratingId);
        oldRating.setTitle(ratingPayload.getTitle());
        oldRating.setContent(ratingPayload.getContent());
        oldRating.setModifiedAt(UtilFunctions.getCurrentDateSql());
        return ratingRepo.save(oldRating);
    }
    public boolean deleteRating(Long ratingId){
        ratingRepo.deleteById(ratingId);
        return true;
    }


}
