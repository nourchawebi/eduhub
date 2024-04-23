package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository <Club,Long>{
}
