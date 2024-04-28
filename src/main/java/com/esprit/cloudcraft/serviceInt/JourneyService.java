package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;

import java.util.List;

public interface JourneyService {
    Journey addJourney(Journey journey, User user);
    Journey modifyJourney(Integer journeyId,Journey journey);
    List<Journey> getAllJourneys();
    void deleteJourneyById(Integer journeyId);

    //Journey addPassengerToJourney(Integer userId, Integer journeyId);

    List<Journey> getJourneysByMotorized(User user);
}
