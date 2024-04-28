package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChapterRepo extends JpaRepository<Chapter,Long> {

}
