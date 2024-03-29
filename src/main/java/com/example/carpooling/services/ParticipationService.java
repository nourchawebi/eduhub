package com.example.carpooling.services;

import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.Participation;

import java.util.List;

public interface ParticipationService {

    Participation addParticipation(Integer idCarpooled, Integer idJourney);
    List<Participation> getJourneyParticipations(Integer idJourney);
    List<Participation> getCarpoolerParticipations(Integer idParticipation);

    void removeParticipation(Integer idParticipation);

}
