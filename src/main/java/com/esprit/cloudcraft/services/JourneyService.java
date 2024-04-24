package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.entities.Journey;

import java.util.List;

public interface JourneyService {
    Journey addJourney(Journey journey);
    Journey modifyJourney(Integer journeyId,Journey journey);
    List<Journey> getAllJourneys();
    void deleteJourneyById(Integer journeyId);

    //Journey addPassengerToJourney(Integer userId, Integer journeyId);

    List<Journey> getJourneysByMotorized(User user);
}
