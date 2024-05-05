package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepo extends JpaRepository<Content,Long> {
}
