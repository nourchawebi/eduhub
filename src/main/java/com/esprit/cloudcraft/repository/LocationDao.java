package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDao extends JpaRepository<Location,Integer> {
}
