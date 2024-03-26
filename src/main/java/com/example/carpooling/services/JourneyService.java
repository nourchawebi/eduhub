package com.example.carpooling.services;

import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.User;

import java.util.List;

public interface JourneyService {
    Journey addJourney(Journey journey);
    Journey modifyJourney(Integer journeyId,Journey journey);
    List<Journey> getAllJourneys();
    void deleteJourneyById(Integer journeyId);

    //Journey addPassengerToJourney(Integer userId, Integer journeyId);

    List<Journey> getJourneysByMotorized(User user);
}
