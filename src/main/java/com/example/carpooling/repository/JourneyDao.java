package com.example.carpooling.repository;

import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.Participation;
import com.example.carpooling.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyDao extends JpaRepository<Journey,Integer> {
    List<Journey> getJourneysByMotorized(User user);
    List<Journey> getJourneysByParticipationsIn(List<Participation> participations);
}
