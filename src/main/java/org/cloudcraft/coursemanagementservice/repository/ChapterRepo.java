package org.cloudcraft.coursemanagementservice.repository;

import org.cloudcraft.coursemanagementservice.module.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ChapterRepo extends JpaRepository<Chapter,Long> {

}
