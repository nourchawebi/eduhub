package org.cloudcraft.coursemanagementservice.repository;


import org.cloudcraft.coursemanagementservice.module.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {
}
