package com.esprit.cloudcraft.repository;


import com.esprit.cloudcraft.entities.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepo extends JpaRepository<Summary,Long> {


    @Query("SELECT s FROM Summary s JOIN Course c ON s.summaryId = c.courseId WHERE c.courseId = :id")
    List<Summary> findSummariesByCourseId(Long id);
}
