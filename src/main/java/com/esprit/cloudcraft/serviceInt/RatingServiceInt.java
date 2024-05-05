package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.entities.Rating;
import com.esprit.cloudcraft.entities.userEntities.User;

public interface RatingServiceInt {

    public Rating addRating(RatingPayload ratingPayload, User user);

    public Rating updateRating(Long ratingId,RatingPayload ratingPayload );
    public boolean deleteRating(Long ratingId);


}
