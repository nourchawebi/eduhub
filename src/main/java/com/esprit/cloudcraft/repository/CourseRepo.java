package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.module.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    Optional<Course> findCourseByName(String name);
}
