package org.cloudcraft.coursemanagementservice.service;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.dto.PayloadSerialization;
import org.cloudcraft.coursemanagementservice.dto.RatingPayload;
import org.cloudcraft.coursemanagementservice.exception.ResourceNotFoundException;
import org.cloudcraft.coursemanagementservice.module.Rating;
import org.cloudcraft.coursemanagementservice.repository.RatingRepo;
import org.cloudcraft.coursemanagementservice.serviceInt.RatingServiceInt;
import org.cloudcraft.coursemanagementservice.utils.UtilFunctions;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

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
