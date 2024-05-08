package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.dto.RateAvg;
import com.esprit.cloudcraft.entities.Feedback;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackDao extends JpaRepository<Feedback,Long> {

    Feedback findByUserAndJourney(User user, Journey journey);
    List<Feedback> findByJourney(Journey journey);

    @Query(value = "select avg(f.rating) from Feedback as f inner join User as u on f.journey.motorized.id=u.id where u.id=:id")
    Double getAvgRating(@Param("id") Long id);

    @Query(value = "select count(f.rating) from Feedback as f inner join User as u on f.journey.motorized.id=u.id where u.id=:id")
    Integer getcountRating(@Param("id") Long id);

}
