package com.example.carpooling.daos;

import com.example.carpooling.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDao extends JpaRepository<Location,Integer> {
}
