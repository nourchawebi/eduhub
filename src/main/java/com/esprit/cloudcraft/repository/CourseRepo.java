package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    Optional<Course> findCourseByName(String name);




    @Query("SELECT new Map(r.value AS rating, COUNT(c) AS courseCount) " +
            "FROM Course c JOIN c.rating r " +
            "GROUP BY r.value")
    List<Map<String, Object>> countCoursesByRatingValue();

    long countBy();
}
