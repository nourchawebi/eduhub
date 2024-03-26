package com.example.carpooling.daos;

import com.example.carpooling.entities.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationDao extends JpaRepository<Participation,Integer> {
}
