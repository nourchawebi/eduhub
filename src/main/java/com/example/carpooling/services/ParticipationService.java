package com.example.carpooling.services;

import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.Participation;
import com.example.carpooling.entities.User;

import java.util.List;

public interface ParticipationService {

    Participation addParticipation(Integer idCarpooled, Integer idJourney);
    List<Participation> getJourneyParticipations(Integer idJourney);
    List<Participation> getCarpoolerParticipations(Integer idParticipation);

    List<User> getJourneyCarpooled(Integer idJourney);

    List<Journey> getCarpooledJourneys(Integer idCarpooled);

    void removeParticipation(Integer idParticipation);

}
