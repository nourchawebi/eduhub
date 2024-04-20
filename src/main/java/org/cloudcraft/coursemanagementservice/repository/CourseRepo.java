package org.cloudcraft.coursemanagementservice.repository;

import org.cloudcraft.coursemanagementservice.module.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    Optional<Course> findCourseByName(String name);
}
