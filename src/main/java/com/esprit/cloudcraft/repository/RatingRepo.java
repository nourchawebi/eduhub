package com.esprit.cloudcraft.repository;


import com.esprit.cloudcraft.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {
}
