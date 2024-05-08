package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JourneyDao extends JpaRepository<Journey,Integer> {
    List<Journey> getJourneysByMotorized(User user);
    Journey getJourneyByParticipationsContains(Participation participations);

    @Query(value = "select j from Journey as j inner join Participation as p on p.journey.journeyId = j.journeyId inner join User as u on p.carpooled.id = u.id where u.id = :id")
    List<Journey> getJourneysParticipated(@Param("id") Long id);

}
