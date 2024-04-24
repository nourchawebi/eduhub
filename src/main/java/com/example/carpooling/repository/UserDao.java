package com.example.carpooling.repository;

import com.example.carpooling.entities.Participation;
import com.example.carpooling.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
    List<User> getUserByParticipationsIn(List<Participation> participations);
}
