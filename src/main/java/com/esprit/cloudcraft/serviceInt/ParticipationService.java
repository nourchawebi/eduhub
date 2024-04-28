package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;

import java.util.List;

public interface ParticipationService {

    Participation addParticipation(Long idCarpooled, Integer idJourney);
    List<Participation> getJourneyParticipations(Integer idJourney);
    List<Participation> getCarpoolerParticipations(Long idParticipation);

    List<User> getJourneyCarpooled(Integer idJourney);

    List<Journey> getCarpooledJourneys(Long idCarpooled);

    void removeParticipation(Integer idParticipation);

    Boolean checkParticipation(Integer idJourney,Long idCarpooler);
}
