package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.entities.Journey;

import java.util.List;

public interface ParticipationService {

    Participation addParticipation(Integer idCarpooled, Integer idJourney);
    List<Participation> getJourneyParticipations(Integer idJourney);
    List<Participation> getCarpoolerParticipations(Integer idParticipation);

    List<User> getJourneyCarpooled(Integer idJourney);

    List<Journey> getCarpooledJourneys(Integer idCarpooled);

    void removeParticipation(Integer idParticipation);

    Boolean checkParticipation(Integer idJourney);
}
