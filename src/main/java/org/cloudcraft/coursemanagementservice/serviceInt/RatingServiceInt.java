package org.cloudcraft.coursemanagementservice.serviceInt;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.dto.RatingPayload;
import org.cloudcraft.coursemanagementservice.module.Rating;
import org.cloudcraft.coursemanagementservice.repository.RatingRepo;

public interface RatingServiceInt {

    public Rating addRating(RatingPayload ratingPayload);

    public Rating updateRating(Long ratingId,RatingPayload ratingPayload );
    public boolean deleteRating(Long ratingId);


}
