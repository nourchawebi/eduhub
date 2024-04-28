package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.module.Rating;

public interface RatingServiceInt {

    public Rating addRating(RatingPayload ratingPayload);

    public Rating updateRating(Long ratingId,RatingPayload ratingPayload );
    public boolean deleteRating(Long ratingId);


}
