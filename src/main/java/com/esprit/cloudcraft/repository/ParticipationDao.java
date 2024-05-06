package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationDao extends JpaRepository<Participation,Integer> {

    List<Participation> findAllByCarpooled(User carpooled);
    List<Participation> findAllByJourney(Journey journey);
    Boolean existsParticipationByCarpooledAndJourney(User carpooled,Journey journey);

    Participation findByCarpooledAndJourney(User user, Journey journey);
    void deleteByCarpooledAndJourney(User user, Journey journey);

}
