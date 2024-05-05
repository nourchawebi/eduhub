package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.PayloadSerialization;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.exceptions.UnauthorizedActionException;
import com.esprit.cloudcraft.serviceInt.RatingServiceInt;
import com.esprit.cloudcraft.services.userServices.UserService;
import jakarta.annotation.Resource;
import com.esprit.cloudcraft.dto.RatingPayload;
import com.esprit.cloudcraft.exceptions.ResourceNotFoundException;
import com.esprit.cloudcraft.entities.Rating;
import com.esprit.cloudcraft.repository.RatingRepo;
import com.esprit.cloudcraft.utils.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements RatingServiceInt {
    @Resource
    private RatingRepo ratingRepo;
    @Autowired
    private UserService userService;

    public Rating addRating(RatingPayload ratingPayload, User owner) {
        Rating newRating= PayloadSerialization.getRatingFromRatingPayload(ratingPayload);
        newRating.setCreatedAt(UtilFunctions.getCurrentDateSql());
        newRating.setOwner(owner);
        return ratingRepo.save(newRating);
    };

public Rating getRatingById(Long id){
    Rating rating=ratingRepo.findById(id).orElse(null);
    if(rating==null) throw new ResourceNotFoundException("rating",id);
    return rating;
}


    public Rating updateRating(Long ratingId,RatingPayload ratingPayload ){
        Rating oldRating=ratingRepo.findById(ratingId).orElse(null);

        if(oldRating==null) throw new ResourceNotFoundException("rating",ratingId);

        User connecteduser=userService.getConnectedUser();
        if(connecteduser.getEmail()!=oldRating.getOwner().getEmail()){
            throw new UnauthorizedActionException("you can not update rating you dont own");
        }
        oldRating.setTitle(ratingPayload.getTitle());
        oldRating.setContent(ratingPayload.getContent());
        oldRating.setModifiedAt(UtilFunctions.getCurrentDateSql());
        return ratingRepo.save(oldRating);
    }
    public boolean deleteRating(Long ratingId){
        Rating oldRating=ratingRepo.findById(ratingId).orElse(null);

        if(oldRating==null) throw new ResourceNotFoundException("rating",ratingId);

        User connecteduser=userService.getConnectedUser();
        if(connecteduser.getEmail()!=oldRating.getOwner().getEmail()){
            throw new UnauthorizedActionException("you can not update rating you dont own");
        }
        ratingRepo.deleteById(ratingId);
        return true;
    }


}
