package com.example.carpooling.repository;

import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.Participation;
import com.example.carpooling.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationDao extends JpaRepository<Participation,Integer> {

    List<Participation> findAllByCarpooled(User carpooled);
    List<Participation> findAllByJourney(Journey journey);
    Boolean existsParticipationByCarpooledAndJourney(User carpooled,Journey journey);

}
