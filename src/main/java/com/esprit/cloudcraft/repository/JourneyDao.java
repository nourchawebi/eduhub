package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyDao extends JpaRepository<Journey,Integer> {
    List<Journey> getJourneysByMotorized(User user);
    List<Journey> getJourneysByParticipationsIn(List<Participation> participations);
}
