package com.esprit.cloudcraft.repository;


import com.esprit.cloudcraft.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {

    long countBy();

    @Query("SELECT r.value, COUNT(r) FROM Rating r GROUP BY r.value")
    List<Object[]> countRatingsByValue();
}
